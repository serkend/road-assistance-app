package com.example.domain.maps.usecases

import com.example.domain.maps.repository.MapsRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

data class GetRoute @Inject constructor(private val mapsRepository: MapsRepository) {
    suspend operator fun invoke(origin: LatLng, destination: LatLng) =
        mapsRepository.getRoute(origin, destination)
}
