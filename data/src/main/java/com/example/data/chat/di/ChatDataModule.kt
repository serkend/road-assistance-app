package com.example.data.chat.di

import com.example.data.chat.repository.ChatRepositoryImpl
import com.example.domain.chat.repository.ChatRepository
import com.example.domain.requests.repository.RequestsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ChatDataModule {

    @Provides
    fun provideChatRepository(
        firestore: FirebaseFirestore,
        mAuth: FirebaseAuth,
        requestsRepository: RequestsRepository
    ): ChatRepository {
        return ChatRepositoryImpl(firestore, mAuth, requestsRepository)
    }

}