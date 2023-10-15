//package com.example.geeksforgeekschat.di
//
//import com.example.data.repository.AuthRepositoryImpl
//import com.example.data.repository.UserManagerRepositoryImpl
//import com.example.domain.repository.AuthRepository
//import com.example.domain.repository.UserManagerRepository
//import com.example.domain.usecases.auth.IsAuthenticated
//import com.example.domain.usecases.auth.SignIn
//import com.example.domain.usecases.auth.SignOut
//import com.example.domain.usecases.auth.SignUp
//import com.example.domain.usecases.auth.AuthUseCases
//import com.example.domain.usecases.user_manager.GetCurrentUser
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//    @Provides
//    @Singleton
//    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
//
//    @Provides
//    @Singleton
//    fun provideFirebaseFirestore(): FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAuthRepository(
//        mAuth: FirebaseAuth, firestore: FirebaseFirestore
//    ): AuthRepository = AuthRepositoryImpl(mAuth, firestore)
//
//    @Provides
//    @Singleton
//    fun provideUserManagerRepository(
//        mAuth: FirebaseAuth, firestore: FirebaseFirestore
//    ): UserManagerRepository =
//        UserManagerRepositoryImpl(mAuth, firestore)
//
//    @Provides
//    @Singleton
//    fun provideGetUserUseCase(userManagerRepository: UserManagerRepository): GetCurrentUser = GetCurrentUser(
//        userManagerRepository
//    )
//
//    @Provides
//    @Singleton
//    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases = AuthUseCases(
//        signIn = SignIn(authRepository),
//        signOut = SignOut(authRepository),
//        signUp = SignUp(authRepository),
//        isAuthenticated = IsAuthenticated(authRepository)
//    )
//}