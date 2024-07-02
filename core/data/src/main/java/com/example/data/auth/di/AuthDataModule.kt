package com.example.data.auth.di

import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.domain.auth.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        mAuth: FirebaseAuth, firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(mAuth, firestore)

}