package com.example.domain.storage.di

import com.example.domain.storage.repository.StorageRepository
import com.example.domain.storage.usecases.SaveProfilePictureToStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageDomainModule {

    @Provides
    @Singleton
    fun provideSaveProfilePictureToStorageUseCase(storageRepository: StorageRepository) =
        SaveProfilePictureToStorage(storageRepository)

}