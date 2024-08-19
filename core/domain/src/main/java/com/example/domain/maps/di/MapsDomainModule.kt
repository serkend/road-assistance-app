package com.example.domain.maps.di

import com.example.domain.maps.repository.MapsRepository
import com.example.domain.maps.usecases.GetRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapsDomainModule {

    @Provides
    @Singleton
    fun provideGetRouteUseCase(mapsRepository: MapsRepository): GetRoute = GetRoute(mapsRepository)

}
