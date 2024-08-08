package com.example.data_test

import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.data.di.DataModule
import com.example.data_test.repository.FakeAuthRepository
import com.example.domain.auth.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object DataTestModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = FakeAuthRepository()

}
