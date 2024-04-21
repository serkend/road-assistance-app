package com.example.domain.auth.usecases.vehicles

import com.example.domain.vehicles.repository.VehiclesRepository
import javax.inject.Inject

class FetchVehicles @Inject constructor(val vehiclesRepository: VehiclesRepository) {
    suspend operator fun invoke() :
}