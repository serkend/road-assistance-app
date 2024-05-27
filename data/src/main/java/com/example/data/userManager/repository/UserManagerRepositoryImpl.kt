package com.example.data.userManager.repository

import android.util.Log
import com.example.common.Constants
import com.example.common.Resource
import com.example.data.userManager.dto.UserDto
import com.example.data.userManager.mappers.toModel
import com.example.domain.userManager.repository.UserManagerRepository
import com.example.domain.common.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserManagerRepositoryImpl @Inject constructor(
    private val mAuth: FirebaseAuth, private val firestore: FirebaseFirestore
) : UserManagerRepository {

    override suspend fun getRegisteredUsersFromServer() : Flow<Resource<List<User>>> = callbackFlow {
        var snapshotListener: ListenerRegistration? = null
        val loggedUserId = mAuth.currentUser?.uid
        try {
            val userList = mutableListOf<UserDto>()
            val docReference = firestore.collection(Constants.FIREBASE_COLLECTION_USERS)
                .orderBy(Constants.CREATED_AT_FIELD, Query.Direction.DESCENDING)

            snapshotListener = docReference.addSnapshotListener { querySnapshot, e ->
                querySnapshot?.let {
                    if (userList.isNotEmpty()) {
                        userList.clear()
                    }

                    for (document in it.documents) {
                        document.toObject(UserDto::class.java)?.let { user ->
                            if (user.uId != loggedUserId) {
                                userList.add(user)
                            }
                        }
                    }

                    val registeredUsers = userList.map { userEntity ->
                        userEntity.toModel()
                    }

                    trySend(Resource.Success(data = registeredUsers))
                }
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error getRegisteredUserList(): ${e.message}")
            trySend(Resource.Failure(e))
        }
        awaitClose {
            snapshotListener?.remove()
        }
    }

    override suspend fun getCurrentUserFromServer(): Resource<User> {
        return try {
            val uid = mAuth.currentUser?.uid
            uid?.let {
                val docRef = firestore.collection(Constants.FIREBASE_COLLECTION_USERS)
                    .document(uid)
                val docSnapshot = docRef.get().await()
                val user = docSnapshot.toObject<UserDto>() ?: UserDto()
                return Resource.Success(user.toModel())
            }
            return Resource.Failure()
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

   override suspend fun getUserById(userId: String): User? {
       return try {
           val docRef = firestore.collection(Constants.FIREBASE_COLLECTION_USERS).document(userId)
           val docSnapshot = docRef.get().await()
           val user = docSnapshot.toObject<UserDto>() ?: UserDto()
           user.toModel()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
