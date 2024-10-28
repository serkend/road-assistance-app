package com.example.domain.sync.di

import com.example.domain.sync.SyncManager
import com.example.domain.sync.repository.SyncRepository
import com.example.domain.sync.usecases.ObserveSyncStateUseCase
import com.example.domain.sync.usecases.RequestImmediateSyncUseCase
import com.example.domain.sync.usecases.SchedulePeriodicSyncUseCase
import com.example.domain.sync.usecases.SyncUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncDomainModule {

    @Provides
    @Singleton
    fun provideSyncUseCases(syncManager: SyncManager, syncRepository: SyncRepository) = SyncUseCases(
        schedulePeriodicSyncUseCase = SchedulePeriodicSyncUseCase(syncManager),
        requestImmediateSyncUseCase = RequestImmediateSyncUseCase(syncManager, syncRepository),
        observeSyncStateUseCase = ObserveSyncStateUseCase(syncManager)
    )
    
}