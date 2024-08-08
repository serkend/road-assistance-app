package com.example.data_test

import com.example.data.di.DataModule
import com.example.domain.auth.di.AuthDomainModule
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.auth.usecases.auth.IsAuthenticated
import com.example.domain.auth.usecases.auth.SignIn
import com.example.domain.auth.usecases.auth.SignOut
import com.example.domain.auth.usecases.auth.SignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AuthDomainModule::class]
)
object DomainTestModule {
    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases = AuthUseCases(
        signIn = SignIn(authRepository),
        signOut = SignOut(authRepository),
        signUp = SignUp(authRepository),
        isAuthenticated = IsAuthenticated(authRepository)
    )
}