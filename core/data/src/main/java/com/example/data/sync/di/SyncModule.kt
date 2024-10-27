package com.example.data.sync.di

import android.content.Context
import androidx.work.Configuration
import com.example.data.sync.SyncPreferencesImpl
import com.example.data.sync.SyncWorker
import com.example.domain.sync.SyncPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Module

object SyncModule {

    @Provides
    @Singleton
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideSyncPreferences(impl: SyncPreferencesImpl): SyncPreferences = impl

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): SyncWorker {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
            .initialize(context, config)
        return SyncWorker().getInstance(context)
    }


}