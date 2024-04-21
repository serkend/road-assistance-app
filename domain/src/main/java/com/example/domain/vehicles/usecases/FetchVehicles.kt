package com.example.domain.vehicles.usecases

import com.example.domain.vehicles.model.Vehicle
import com.example.domain.vehicles.repository.VehiclesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchVehicles @Inject constructor(val vehiclesRepository: VehiclesRepository) {
    suspend operator fun invoke() : Flow<List<Vehicle>> {
        return vehiclesRepository.getVehicles()
    }
}