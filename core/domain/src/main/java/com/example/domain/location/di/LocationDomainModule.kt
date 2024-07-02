package com.example.domain.location.di

import android.content.Context
import com.example.domain.location.repository.LocationRepository
import com.example.domain.location.usecases.GetCurrentLocation
import com.example.domain.vehicles.repository.VehiclesRepository
import com.example.domain.vehicles.usecases.DeleteVehicle
import com.example.domain.vehicles.usecases.FetchVehicles
import com.example.domain.vehicles.usecases.GetVehicleById
import com.example.domain.vehicles.usecases.SaveVehicle
import com.example.domain.vehicles.usecases.VehiclesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationDomainModule {

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(locationRepository: LocationRepository): GetCurrentLocation =
        GetCurrentLocation(locationRepository)

}
