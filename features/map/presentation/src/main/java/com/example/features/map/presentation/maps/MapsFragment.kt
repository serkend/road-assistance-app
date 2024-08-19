package com.example.features.map.presentation.maps

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.uikit.extensions.bindSharedFlow
import com.example.core.uikit.extensions.bindStateFlow
import com.example.core.uikit.extensions.checkLocationPermission
import com.example.core.uikit.extensions.showToast
import com.example.features.map.presentation.R
import com.example.features.map.presentation.databinding.FragmentMapsBinding
import com.example.features.map.presentation.requestDetails.RequestDetailsBottomSheetFragment.Companion.DRAW_ROUTE_RESULT
import com.example.features.map.presentation.requestDetails.RequestDetailsBottomSheetFragment.Companion.ORDER_DESTINATION
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

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val viewModel: MapsViewModel by viewModels()
    private lateinit var binding: FragmentMapsBinding

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
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() = with(viewModel) {
        bindSharedFlow(launchChooseVehicleTroubleScreen) {
            findNavController().navigate(R.id.chooseVehicleTroubleBottomSheetFragment)
        }
        bindSharedFlow(showToast) { showToast(it) }
        bindSharedFlow(showRouteSharedFlow) { path ->
            mMap?.addPolyline(
                PolylineOptions().addAll(path)
                    .width(16f)
                    .color(requireContext().getColor(com.example.core.uikit.R.color.colorPrimary))
                    .geodesic(true)
            )
        }
        bindStateFlow(currentLocation) { location ->
            location?.let { moveToCurrentLocation(it) }
            destinationLatLng?.let { viewModel.drawRoute() }
        }
        bindStateFlow(markersStateFlow) { requests ->
            mMap?.clear()
            requests.forEach { request ->
                val icon = if (request.isCurrentUser) {
                    BitmapDescriptorFactory.fromResource(com.example.core.uikit.R.drawable.ic_my_car)
                } else {
                    BitmapDescriptorFactory.fromResource(com.example.core.uikit.R.drawable.ic_other_car)
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