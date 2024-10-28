package com.example.data.sync.di

import android.content.Context
import androidx.work.WorkManager
import com.example.core.common.IoDispatcher
import com.example.core.common.MainDispatcher
import com.example.data.sync.SyncManagerImpl
import com.example.data.sync.SyncPreferencesImpl
import com.example.data.sync.repository.SyncRepositoryImpl
import com.example.domain.sync.SyncManager
import com.example.domain.sync.SyncPreferences
import com.example.domain.sync.repository.SyncRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncDataModule {
    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideSyncPreferences(impl: SyncPreferencesImpl): SyncPreferences = impl

    @Provides
    @Singleton
    fun provideSyncRepository(impl: SyncRepositoryImpl): SyncRepository = impl

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun bindSyncManager(impl: SyncManagerImpl): SyncManager = impl

}