package com.example.data.auth.repository

import com.example.core.common.AuthState
import com.example.core.common.Constants
import com.example.core.common.Resource
import com.example.core.common.ResultState
import com.example.data.userManager.dto.UserDto
import com.example.data.userManager.mappers.toDto
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.common.User
import com.example.domain.storage.repository.StorageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val mAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storageRepository: StorageRepository
) : AuthRepository {

    override fun isUserAuthenticated(): Flow<ResultState<Boolean>> =
        callbackFlow {
            try {
                trySend(ResultState.Loading())
                val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                    val isAuthenticated = auth.currentUser != null
                    trySend(ResultState.Success(isAuthenticated))
                }
                mAuth.addAuthStateListener(authStateListener)
                awaitClose {
                    mAuth.removeAuthStateListener(authStateListener)
                }
            } catch (e: Exception) {
                trySend(ResultState.Failure(e.localizedMessage))
            }
        }

    override suspend fun signIn(credentials : SignInCredentials): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading)
            try {
                mAuth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
                emit(Resource.Success())
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    override suspend fun signUp(credentials : SignUpCredentials, user: User): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading)
            mAuth.createUserWithEmailAndPassword(credentials.email, credentials.password).await()
            val imageUrl = credentials.imageUri?.let { storageRepository.saveProfilePictureToStorage(it) }
            saveCurrUserToFirebase(user.toDto().copy(image = imageUrl))
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    override suspend fun signOut(): Resource<Boolean> = try {
        mAuth.signOut()
        Resource.Success(true)
    } catch (e: Exception) {
        Resource.Failure(e)
    }

    private suspend fun saveCurrUserToFirebase(userDto: UserDto) {
        val uid = mAuth.currentUser?.uid
        uid?.let {
            val user = userDto.copy(uId = uid)

            firestore.collection(Constants.FIREBASE_COLLECTION_USERS)
                .document(user.uId)
                .set(user)
                .await()
        }
    }

}