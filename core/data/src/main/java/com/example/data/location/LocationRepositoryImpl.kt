package com.example.data.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.example.core.common.ResultState
import com.example.domain.location.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val fusedLocationProviderClient: FusedLocationProviderClient): LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<ResultState<Location>> = callbackFlow {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                trySend(ResultState.Success(data = locationResult.lastLocation))
            }
        }

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ).addOnFailureListener {
            trySend(ResultState.Failure(e = it.localizedMessage))
        }

        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }
}
