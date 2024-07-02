package com.example.domain.vehicles.di

import com.example.domain.vehicles.repository.VehiclesRepository
import com.example.domain.vehicles.usecases.DeleteVehicle
import com.example.domain.vehicles.usecases.FetchVehicles
import com.example.domain.vehicles.usecases.GetVehicleById
import com.example.domain.vehicles.usecases.SaveVehicle
import com.example.domain.vehicles.usecases.VehiclesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VehiclesDomainModule {
    @Provides
    @Singleton
    fun provideVehiclesUseCases(vehiclesRepository: VehiclesRepository): VehiclesUseCases = VehiclesUseCases(
        fetchVehicles = FetchVehicles(vehiclesRepository),
        saveVehicle = SaveVehicle(vehiclesRepository),
        deleteVehicle = DeleteVehicle(vehiclesRepository),
        getVehicleById = GetVehicleById(vehiclesRepository)
    )
}