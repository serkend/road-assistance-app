package com.example.domain.vehicles.repository

import com.example.domain.vehicles.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehiclesRepository {
    suspend fun fetchVehicles(): Flow<List<Vehicle>>
}