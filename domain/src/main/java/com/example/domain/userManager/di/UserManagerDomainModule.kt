package com.example.domain.userManager.di

import com.example.domain.auth.repository.UserManagerRepository
import com.example.domain.userManager.usecases.GetCurrentUser
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
    fun provideGetUserUseCase(userManagerRepository: UserManagerRepository): GetCurrentUser = GetCurrentUser(
        userManagerRepository
    )
}