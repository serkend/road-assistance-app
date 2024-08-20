package com.example.domain.maps.di

import com.example.domain.maps.repository.LocationRepository
import com.example.domain.maps.usecases.GetCurrentLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
