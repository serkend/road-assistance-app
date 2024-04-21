package com.example.domain.auth.di

import com.example.domain.auth.repository.AuthRepository
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.IsAuthenticated
import com.example.domain.auth.usecases.auth.SignIn
import com.example.domain.auth.usecases.auth.SignOut
import com.example.domain.auth.usecases.auth.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {
    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases = AuthUseCases(
        signIn = SignIn(authRepository),
        signOut = SignOut(authRepository),
        signUp = SignUp(authRepository),
        isAuthenticated = IsAuthenticated(authRepository)
    )

}