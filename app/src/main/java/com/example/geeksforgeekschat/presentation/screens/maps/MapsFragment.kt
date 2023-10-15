package com.example.geeksforgeekschat.presentation.screens.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.Constants.TAG
import com.example.geeksforgeekschat.R
import com.example.geeksforgeekschat.presentation.utils.bindStateFlow
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var currentLocation: Location? = null
    private val viewModel: MapsViewModel by viewModels()

    private var locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Is granted", Toast.LENGTH_SHORT).show()

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                if (checkLocationPermission()) {
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
                            Log.e(
                                TAG,
                                "current location: ${location.latitude} ${location.longitude}"
                            )
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Is not granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        if (checkLocationPermission()) {
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
        bindStateFlow(viewModel.markersPosSharedFlow) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

}

fun Fragment.checkLocationPermission(): Boolean {
    if (ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return false
    }
    return true
}