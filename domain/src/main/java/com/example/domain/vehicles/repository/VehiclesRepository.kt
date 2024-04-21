package com.example.domain.vehicles.repository

import com.example.common.ResultState
import com.example.domain.vehicles.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehiclesRepository {
    suspend fun fetchVehicles(): Flow<ResultState<List<Vehicle>>>
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
}