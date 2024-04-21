package com.example.domain.vehicles.usecases

import com.example.common.ResultState
import com.example.domain.vehicles.model.Vehicle
import com.example.domain.vehicles.repository.VehiclesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveVehicle @Inject constructor(private val vehiclesRepository: VehiclesRepository) {
    suspend operator fun invoke(vehicle: Vehicle) {
        return vehiclesRepository.saveVehicle(vehicle)
    }
}