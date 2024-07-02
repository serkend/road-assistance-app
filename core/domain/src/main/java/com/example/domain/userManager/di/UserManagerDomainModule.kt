package com.example.domain.userManager.di

import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.userManager.usecases.GetCurrentUser
import com.example.domain.userManager.usecases.GetUserById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserManagerDomainModule {
    @Provides
    @Singleton
    fun provideGetUserUseCase(userManagerRepository: UserManagerRepository): GetCurrentUser =
        GetCurrentUser(
            userManagerRepository
        )

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(userManagerRepository: UserManagerRepository): GetUserById =
        GetUserById(
            userManagerRepository
        )
}