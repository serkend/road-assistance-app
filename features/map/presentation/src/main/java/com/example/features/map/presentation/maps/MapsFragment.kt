package com.example.features.map.presentation.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.uikit.extensions.bindFlow
import com.example.core.uikit.extensions.checkLocationPermission
import com.example.core.uikit.extensions.showToast
import com.example.features.map.presentation.R
import com.example.features.map.presentation.databinding.FragmentMapsBinding
import com.example.features.map.presentation.requestDetails.RequestDetailsBottomSheetFragment.Companion.DRAW_ROUTE_RESULT
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private var mMap: GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val viewModel: MapsViewModel by viewModels()
    private lateinit var binding: FragmentMapsBinding

    private val enableLocationLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            setupMap()
        } else {
            showToast(getString(R.string.location_required))
        }
    }

    @SuppressLint("MissingPermission")
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Is granted", Toast.LENGTH_SHORT).show()
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                viewModel.getMyCurrentLocation()
            } else {
                showToast(getString(R.string.is_not_granted))
            }
        }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        setupMap()
    }

    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)

        LocationServices.getSettingsClient(requireActivity())
            .checkLocationSettings(builder.build())
            .addOnSuccessListener {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        enableLocationLauncher.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // TODO ignore error
                    }
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {
        if (requireContext().checkLocationPermission()) {
            mMap?.isMyLocationEnabled = true
            viewModel.currentLocation.value?.let { moveToCurrentLocation(it) }
            observeRouteDrawingRequest()
        }
        mMap?.setOnMarkerClickListener { marker ->
            val request = marker.tag as? RequestModel
            request?.let {
                val action = MapsFragmentDirections.actionMapsFragmentToRequestDetailsBottomSheetFragment(it)
                findNavController().navigate(action)
            }
            true
        }
    }

    private fun moveToCurrentLocation(currentLatLng: LatLng) {
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
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

        checkLocationSettings()
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() = with(viewModel) {
        bindFlow(launchChooseVehicleTroubleScreen) {
            findNavController().navigate(R.id.chooseVehicleTroubleBottomSheetFragment)
        }
        bindFlow(showToast) { showToast(it) }
        bindFlow(showRouteSharedFlow) { path ->
            mMap?.addPolyline(
                PolylineOptions().addAll(path)
                    .width(16f)
                    .color(requireContext().getColor(com.example.core.uikit.R.color.colorPrimary))
                    .geodesic(true)
            )
        }
        bindFlow(currentLocation) { location ->
            location?.let { moveToCurrentLocation(it) }
            destinationLatLng?.let { viewModel.drawRoute() }
        }
        bindFlow(markersStateFlow) { requests ->
            mMap?.clear()
            requests.forEach { request ->
                val icon = if (request.isCurrentUser) {
                    BitmapDescriptorFactory.fromResource(com.example.core.uikit.R.drawable.ic_my_car)
                } else {
                    BitmapDescriptorFactory.fromResource(com.example.core.uikit.R.drawable.ic_other_car)
                }
                val latLng = LatLng(request.latitude, request.longitude)
                val marker = mMap?.addMarker(
                    MarkerOptions().position(latLng).icon(icon)
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
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Bundle>(DRAW_ROUTE_RESULT)
            ?.observe(viewLifecycleOwner) { bundle ->
                bundle?.let {
                    viewModel.getDestinationAndDrawRoute()
                }
            }
    }

}