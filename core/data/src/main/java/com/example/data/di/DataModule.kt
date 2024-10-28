package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.data.chat.repository.ChatRepositoryImpl
import com.example.data.database.AppDatabase
import com.example.data.fileManager.repository.FileManagerRepositoryImpl
import com.example.data.maps.GoogleMapsApi
import com.example.data.maps.LocationRepositoryImpl
import com.example.data.maps.MapsRepositoryImpl
import com.example.data.requests.dao.RequestsDao
import com.example.data.requests.repository.RequestsRepositoryImpl
import com.example.data.storage.repository.StorageRepositoryImpl
import com.example.data.userManager.repository.UserManagerRepositoryImpl
import com.example.data.vehicles.dao.VehicleDao
import com.example.data.vehicles.repository.VehicleRepositoryImpl
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
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        firestore: FirebaseFirestore,
        mAuth: FirebaseAuth,
        requestsRepository: RequestsRepository,
        chatRepository: StorageRepository
    ): ChatRepository {
        return ChatRepositoryImpl(firestore, mAuth, requestsRepository, chatRepository)
    }

    @Provides
    @Singleton
    fun provideRequestsDao(database: AppDatabase): RequestsDao {
        return database.requestsDao()
    }

    @Provides
    @Singleton
    fun provideRequestsRepository(firestore: FirebaseFirestore, mAuth: FirebaseAuth, requestsDao: RequestsDao): RequestsRepository {
        return RequestsRepositoryImpl(firestore, mAuth, requestsDao)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(fusedLocationProviderClient: FusedLocationProviderClient): LocationRepository {
        return LocationRepositoryImpl(fusedLocationProviderClient)
    }

    @Provides
    @Singleton
    fun provideUserManagerRepository(
        mAuth: FirebaseAuth, firestore: FirebaseFirestore
    ): UserManagerRepository = UserManagerRepositoryImpl(mAuth, firestore)

    @Provides
    @Singleton
    fun provideVehicleDao(database: AppDatabase): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    @Singleton
    fun provideVehicleRepository(firestore: FirebaseFirestore, vehicleDao: VehicleDao): VehiclesRepository {
        return VehicleRepositoryImpl(firestore, vehicleDao)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        mAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        storageRepository: StorageRepository
    ): AuthRepository = AuthRepositoryImpl(mAuth, firestore, storageRepository)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStorageRepository(): StorageRepository = StorageRepositoryImpl()

    @Provides
    @Singleton
    fun provideFileManagerRepository(@ApplicationContext context: Context): FileManagerRepository = FileManagerRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideGoogleMapsApi(): GoogleMapsApi {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleMapsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMapsRepository(googleMapsApi: GoogleMapsApi): MapsRepository = MapsRepositoryImpl(googleMapsApi)

    private const val DATABASE_NAME = "road_assist_database"

}