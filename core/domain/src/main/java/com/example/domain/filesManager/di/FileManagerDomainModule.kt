package com.example.domain.filesManager.di

import com.example.domain.filesManager.repository.FileManagerRepository
import com.example.domain.filesManager.usecases.DownloadImage
import com.example.domain.storage.repository.StorageRepository
import com.example.domain.storage.usecases.SaveProfilePictureToStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FileManagerDomainModule {
    @Provides
    @Singleton
    fun provideDownloadImageUseCase(fileManagerRepository: FileManagerRepository) =
        DownloadImage(fileManagerRepository)

}