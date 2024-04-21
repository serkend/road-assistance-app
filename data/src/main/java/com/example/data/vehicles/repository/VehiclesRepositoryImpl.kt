package com.example.data.vehicles.repository

import com.example.data.vehicles.dao.VehicleDao
import com.example.domain.vehicles.model.Vehicle
import com.example.domain.vehicles.repository.VehiclesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehiclesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val vehicleDao: VehicleDao,

) : VehiclesRepository {
    override suspend fun fetchVehicles(): Flow<List<Vehicle>> {

    }
}