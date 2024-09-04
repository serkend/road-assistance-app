package com.example.data_test

import android.content.Context
import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.data.chat.repository.ChatRepositoryImpl
import com.example.data.database.AppDatabase
import com.example.data.di.DataModule
import com.example.data.maps.GoogleMapsApi
import com.example.data.vehicles.dao.VehicleDao
import com.example.data_test.repository.FakeAuthRepository
import com.example.data_test.repository.FakeChatRepository
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.chat.repository.ChatRepository
import com.example.domain.filesManager.repository.FileManagerRepository
import com.example.domain.maps.repository.LocationRepository
import com.example.domain.maps.repository.MapsRepository
import com.example.domain.requests.repository.RequestsRepository
import com.example.domain.storage.repository.StorageRepository
import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.vehicles.repository.VehiclesRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAuthRepository(): AuthRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideRequestsRepository(): RequestsRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext
    context: Context
    ): FusedLocationProviderClient {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(): LocationRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideUserManagerRepository(): UserManagerRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideVehicleRepository(): VehiclesRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideStorageRepository(): StorageRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideFileManagerRepository(): FileManagerRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideGoogleMapsApi(): GoogleMapsApi {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideMapsRepository(): MapsRepository {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return mockk()
    }

    @Provides
    @Singleton
    fun provideVehicleDao(): VehicleDao {
        return mockk()
    }
}
