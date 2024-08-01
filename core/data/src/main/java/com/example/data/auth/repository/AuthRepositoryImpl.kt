package com.example.data.auth.repository

import android.net.Uri
import android.util.Log
import com.example.core.common.Constants
import com.example.core.common.Constants.TAG
import com.example.core.common.Resource
import com.example.data.userManager.mappers.toDto
import com.example.data.userManager.dto.UserDto
import com.example.domain.auth.model.SignInCredentials
import com.example.domain.auth.model.SignUpCredentials
import com.example.domain.common.User
import com.example.domain.auth.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import kotlin.Exception

class AuthRepositoryImpl @Inject constructor(
    private val mAuth: FirebaseAuth, private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun isUserAuthenticated(viewModelScope: CoroutineScope): StateFlow<Boolean> =
        callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser != null)
            }
            mAuth.addAuthStateListener(authStateListener)
            awaitClose {
                mAuth.removeAuthStateListener(authStateListener)
            }
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = mAuth.currentUser != null
        )

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
            val imageUrl = credentials.imageUri?.let { saveProfilePictureToStorage(it) }
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

    private suspend fun saveProfilePictureToStorage(imageUri: Uri): String {
        return try {
            val storageRef = Firebase.storage.reference
            val imageName = UUID.randomUUID().toString()
            val profilePicturesRef = storageRef.child("profile_images/$imageName")
            val profilePictureUrl =
                profilePicturesRef.putFile(imageUri).await().storage.downloadUrl.await()
            profilePictureUrl.toString()
        } catch (e: Exception) {
            Log.e(TAG, "saveProfilePictureToStorage error : ${e.message}")
            ""
        }
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