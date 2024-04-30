package com.example.domain.request.repository

import com.example.common.ResultState
import com.example.domain.vehicles.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {
    suspend fun fetchRequest(): Flow<ResultState<List<Vehicle>>>
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
}