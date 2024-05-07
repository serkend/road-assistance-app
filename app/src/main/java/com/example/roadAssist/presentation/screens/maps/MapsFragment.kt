package com.example.roadAssist.presentation.screens.maps

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
import com.example.common.Constants.TAG
import com.example.roadAssist.R
import com.example.roadAssist.databinding.FragmentMapsBinding
import com.example.roadAssist.presentation.utils.bindSharedFlow
import com.example.roadAssist.presentation.utils.bindStateFlow
import com.example.common.extensions.checkLocationPermission
import com.example.common.extensions.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var currentLocation: Location? = null
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
                            currentLocation = location
                            mMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        currentLocation?.latitude ?: 0.0,
                                        currentLocation?.longitude ?: 0.0
                                    ), 15f
                                )
                            )
                            Log.e(TAG, "current location: ${location.latitude} ${location.longitude}")
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Is not granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        if (requireContext().checkLocationPermission()) {
            mMap?.isMyLocationEnabled = true
            mMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        currentLocation?.latitude ?: 0.0,
                        currentLocation?.longitude ?: 0.0
                    ), 15f
                )
            )
        }
        bindStateFlow(viewModel.markersPosStateFlow) {
            mMap?.clear()
            it.forEach { latLng ->
                mMap?.addMarker(
                    MarkerOptions().position(latLng)
                        .draggable(true)
                        .visible(true)
                        .title("${latLng.latitude} ${latLng.longitude}")
                )
            }
        }
        mMap?.setOnMapLongClickListener { latLong ->
            viewModel.addMarker(latLong)
        }
        mMap?.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            true
        }
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
        bindStateFlow(markersStateFlow) { markers ->
            mMap?.clear()
            markers.forEach { (latLng, isCurrentUser) ->
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .icon(if (isCurrentUser) BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) else BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                mMap?.addMarker(markerOptions)
            }
        }
    }

    private fun initViews() = with(binding) {
        requestAssistButton.setOnClickListener {
            viewModel.onRequestAssistClicked()
        }
    }

}