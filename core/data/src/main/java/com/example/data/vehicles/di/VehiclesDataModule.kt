package com.example.data.vehicles.di

import com.example.data.database.AppDatabase
import com.example.data.vehicles.dao.VehicleDao
import com.example.data.vehicles.repository.VehicleRepositoryImpl
import com.example.domain.vehicles.repository.VehiclesRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object VehiclesDataModule {

    @Provides
    fun provideVehicleDao(database: AppDatabase): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    fun provideVehicleRepository(firestore: FirebaseFirestore, vehicleDao: VehicleDao): VehiclesRepository {
        return VehicleRepositoryImpl(firestore, vehicleDao)
    }

}