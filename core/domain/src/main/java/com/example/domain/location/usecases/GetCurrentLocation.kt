package com.example.domain.location.usecases

import android.location.Location
import com.example.core.common.ResultState
import com.example.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(private val locationRepository: LocationRepository) {
    operator fun invoke(): Flow<ResultState<Location>> = locationRepository.getCurrentLocation()
}