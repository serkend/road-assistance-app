package com.example.domain.maps.repository

import com.example.core.common.ResultState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapsRepository {
    suspend fun getRoute(origin: LatLng, destination: LatLng): Flow<ResultState<List<LatLng>>>
}