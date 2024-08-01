package com.example.domain.vehicles.repository

import com.example.core.common.ResultState
import com.example.domain.vehicles.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehiclesRepository {
    suspend fun fetchVehicles(): Flow<ResultState<List<Vehicle>>>
    suspend fun getVehicleById(vehicleId: String): Vehicle
    suspend fun saveVehicle(vehicle: Vehicle): String
    suspend fun deleteVehicle(vehicle: Vehicle)

}