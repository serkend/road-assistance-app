package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.data.chat.repository.ChatRepositoryImpl
import com.example.data.database.AppDatabase
import com.example.data.location.LocationRepositoryImpl
import com.example.data.requests.repository.RequestsRepositoryImpl
import com.example.data.userManager.repository.UserManagerRepositoryImpl
import com.example.data.vehicles.dao.VehicleDao
import com.example.data.vehicles.repository.VehicleRepositoryImpl
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.chat.repository.ChatRepository
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.chat.usecases.GetConversations
import com.example.domain.chat.usecases.GetMessages
import com.example.domain.chat.usecases.GetOrCreateConversation
import com.example.domain.chat.usecases.GetReceiverIdForConversation
import com.example.domain.chat.usecases.SendMessage
import com.example.domain.location.repository.LocationRepository
import com.example.domain.requests.repository.RequestsRepository
import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.vehicles.repository.VehiclesRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        firestore: FirebaseFirestore,
        mAuth: FirebaseAuth,
        requestsRepository: RequestsRepository
    ): ChatRepository {
        return ChatRepositoryImpl(firestore, mAuth, requestsRepository)
    }

    @Provides
    @Singleton
    fun provideRequestsRepository(firestore: FirebaseFirestore, mAuth: FirebaseAuth): RequestsRepository {
        return RequestsRepositoryImpl(firestore, mAuth)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext
    context: Context
    ): FusedLocationProviderClient {
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
        mAuth: FirebaseAuth, firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(mAuth, firestore)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    private const val DATABASE_NAME = "road_assist_database"

}