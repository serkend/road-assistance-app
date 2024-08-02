package com.example.features.map.presentation.maps

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.common.Constants.TAG
import com.example.core.common.extensions.bindSharedFlow
import com.example.core.common.extensions.bindStateFlow
import com.example.core.common.extensions.checkLocationPermission
import com.example.core.common.extensions.showToast
import com.example.features.map.presentation.R
import com.example.features.map.presentation.databinding.FragmentMapsBinding
//import com.example.features.map.presentation.requestDetails.RequestDetailsBottomSheetFragment.Companion.DRAW_ROUTE_RESULT
//import com.example.features.map.presentation.requestDetails.RequestDetailsBottomSheetFragment.Companion.ORDER_DESTINATION
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var currentLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null
    private val viewModel: MapsViewModel by viewModels()
    private lateinit var binding: FragmentMapsBinding

    @SuppressLint("MissingPermission")
    private var locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Is granted", Toast.LENGTH_SHORT).show()

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                if (requireContext().checkLocationPermission()) {
                    fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
                        location?.let {
                            currentLatLng = LatLng(it.latitude, it.longitude)
                            moveToCurrentLocation(currentLatLng!!)
                            Log.e(TAG, "current location: ${location.latitude} ${location.longitude}")
                        }
                    }
                }
            } else {
                requireContext().showToast("Is not granted")
            }
        }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        if (requireContext().checkLocationPermission()) {
            mMap?.isMyLocationEnabled = true
            currentLatLng?.let { moveToCurrentLocation(it) }
            observeRouteDrawingRequest()
        }

        mMap?.setOnMarkerClickListener { marker ->
            val request = marker.tag as? RequestModel
            request?.let {
//                if (viewModel.hasOrder) {
                    val action =
                        MapsFragmentDirections.actionMapsFragmentToRequestDetailsBottomSheetFragment(
                            it
                        )
                    findNavController().navigate(action)
//                } else {
//                    showToast("You already have an active order.")
//                }
            }
            true
        }
    }

    private fun moveToCurrentLocation(currentLatLng: LatLng) {
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(currentLatLng.latitude, currentLatLng.longitude), 15f
            )
        )
//        viewModel.fetchCurrentUserOrder()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() = with(viewModel) {
        bindSharedFlow(launchChooseVehicleTroubleScreen) {
            Log.e(TAG, "bindViewModel: launchChooseVehicleTroubleScreen")
            findNavController().navigate(R.id.chooseVehicleTroubleBottomSheetFragment)
        }
        bindSharedFlow(showToast) { requireContext().showToast(it) }
        bindSharedFlow(showRouteSharedFlow) { destination ->
            destinationLatLng = destination
            currentLatLng?.let { current -> drawRouteToDestination(current, destination) }
        }
        bindStateFlow(markersStateFlow) { requests ->
            mMap?.clear()
            requests.forEach { request ->
                val icon = if (request.isCurrentUser) {
                    BitmapDescriptorFactory.fromResource(com.example.core.common.R.drawable.ic_my_car)
                } else {
                    BitmapDescriptorFactory.fromResource(com.example.core.common.R.drawable.ic_other_car)
                }
                val latLng = LatLng(request.latitude, request.longitude)
                val marker = mMap?.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(icon)
                )
                marker?.tag = request
            }
        }

    }

    private fun initViews() = with(binding) {
        requestAssistButton.setOnClickListener {
            viewModel.onRequestAssistClicked()
        }
    }

    private fun observeRouteDrawingRequest() {
//        findNavController()
//            .currentBackStackEntry
//            ?.savedStateHandle
//            ?.getLiveData<Bundle>(DRAW_ROUTE_RESULT)
//            ?.observe(viewLifecycleOwner) { bundle ->
//                bundle?.let {
//                    val destination = it.getParcelable<LatLng>(ORDER_DESTINATION)
//                    destination?.let { latLng ->
//                        destinationLatLng = latLng
//                        viewModel.fetchCurrentUserOrder()
//                    }
//                }
//            }
    }

    private fun drawRouteToDestination(origin: LatLng?, destination: LatLng?) {
        if (origin != null && destination != null) {
            val originStr = "${origin.latitude},${origin.longitude}"
            val destinationStr = "${destination.latitude},${destination.longitude}"

            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            val googleMapsApi = retrofit.create(GoogleMapsApi::class.java)
            googleMapsApi.getDirections(originStr, destinationStr)
                .enqueue(object : Callback<DirectionsResponse> {
                    override fun onResponse(
                        call: Call<DirectionsResponse>,
                        response: Response<DirectionsResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.routes?.firstOrNull()?.let { route ->
                                val path = ArrayList<LatLng>()
                                route.legs.forEach { leg ->
                                    leg.steps.forEach { step ->
                                        path.addAll(decodePoly(step.polyline.points))
                                    }
                                }
                                mMap?.addPolyline(
                                    PolylineOptions().addAll(path)
                                        .width(16f)
                                        .color(requireContext().getColor(com.example.core.common.R.color.colorPrimary))
                                        .geodesic(true)
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                        Log.e("MapsActivity", "Error fetching directions: ${t.message}")
                        showToast("Error fetching directions")
                    }
                })
        }
    }

    fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }

}