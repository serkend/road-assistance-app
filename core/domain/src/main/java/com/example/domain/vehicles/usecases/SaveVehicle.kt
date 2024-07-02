package com.example.domain.vehicles.usecases

import com.example.domain.vehicles.model.Vehicle
import com.example.domain.vehicles.repository.VehiclesRepository
import javax.inject.Inject

class SaveVehicle @Inject constructor(private val vehiclesRepository: VehiclesRepository) {
    suspend operator fun invoke(vehicle: Vehicle): String {
        return vehiclesRepository.saveVehicle(vehicle)
    }
}