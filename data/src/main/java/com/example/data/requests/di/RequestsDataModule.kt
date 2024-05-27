package com.example.data.requests.di

import com.example.data.requests.repository.RequestsRepositoryImpl
import com.example.domain.requests.repository.RequestsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RequestsDataModule {

    @Provides
    fun provideRequestsRepository(firestore: FirebaseFirestore, mAuth: FirebaseAuth): RequestsRepository {
        return RequestsRepositoryImpl(firestore, mAuth)
    }

}