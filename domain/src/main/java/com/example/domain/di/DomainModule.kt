package com.example.domain.di

import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserManagerRepository
import com.example.domain.usecases.auth.AuthUseCases
import com.example.domain.usecases.auth.IsAuthenticated
import com.example.domain.usecases.auth.SignIn
import com.example.domain.usecases.auth.SignOut
import com.example.domain.usecases.auth.SignUp
import com.example.domain.usecases.user_manager.GetCurrentUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideGetUserUseCase(userManagerRepository: UserManagerRepository): GetCurrentUser = GetCurrentUser(
        userManagerRepository
    )

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases = AuthUseCases(
        signIn = SignIn(authRepository),
        signOut = SignOut(authRepository),
        signUp = SignUp(authRepository),
        isAuthenticated = IsAuthenticated(authRepository)
    )
}