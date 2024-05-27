package com.example.data.userManager.di

import com.example.data.userManager.repository.UserManagerRepositoryImpl
import com.example.domain.userManager.repository.UserManagerRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserManagerDataModule {

    @Provides
    @Singleton
    fun provideUserManagerRepository(
        mAuth: FirebaseAuth, firestore: FirebaseFirestore
    ): UserManagerRepository =
        UserManagerRepositoryImpl(mAuth, firestore)

}