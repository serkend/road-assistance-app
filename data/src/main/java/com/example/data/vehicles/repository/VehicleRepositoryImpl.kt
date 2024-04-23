package com.example.data.vehicles.repository

import com.example.common.ResultState
import com.example.data.vehicles.dao.VehicleDao
import com.example.data.vehicles.dto.VehicleDto
import com.example.data.vehicles.entity.VehicleEntity
import com.example.data.vehicles.entity.toDomain
import com.example.domain.vehicles.model.Vehicle
import com.example.domain.vehicles.repository.VehiclesRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val vehicleDao: VehicleDao,
) : VehiclesRepository {
    override suspend fun fetchVehicles(): Flow<ResultState<List<Vehicle>>> = flow {
        val lastCachedVehicles = withContext(Dispatchers.IO) {
            vehicleDao.getAllVehicles().firstOrNull()?.map { it.toDomain() }
        }
        emit(ResultState.Loading(data = lastCachedVehicles))
        try {
            val snapshot = firestore.collection(VehicleDto.FIREBASE_VEHICLES).get().await()
            val serverVehicles = snapshot.documents.mapNotNull { doc ->
                VehicleEntity(
                    id = doc.id,
                    make = doc.getString(VehicleDto.FIREBASE_MAKE) ?: "",
                    model = doc.getString(VehicleDto.FIREBASE_MODEL) ?: "",
                    year = doc.getLong(VehicleDto.FIREBASE_YEAR)?.toInt() ?: 0
                )
            }
            withContext(Dispatchers.IO) {
                vehicleDao.insertVehicles(serverVehicles)
            }
            emit(ResultState.Success(data = serverVehicles.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultState.Failure(e.message ?: "Unknown Error", data = lastCachedVehicles))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveVehicle(vehicle: Vehicle) {
        withContext(Dispatchers.IO) {
            val newVehicleDocument = firestore.collection("vehicles").document()
            val newVehicle = vehicle.copy(id = newVehicleDocument.id)
            try {
                newVehicleDocument.set(newVehicle).await()
//                vehicleDao.insertVehicle(newVehicle.toEntity())
            } catch (e: Exception) {
                throw RuntimeException("Failed to save vehicle", e)
            }
        }
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        withContext(Dispatchers.IO) {
            firestore.collection("vehicles").document(vehicle.id).delete().await()
//            vehicleDao.deleteVehicle(vehicle.toEntity())
        }
    }
}