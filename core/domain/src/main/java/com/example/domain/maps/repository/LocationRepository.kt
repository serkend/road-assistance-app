package com.example.domain.maps.repository

import android.location.Location
import com.example.core.common.ResultState
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCurrentLocation(): Flow<ResultState<Location>>

}