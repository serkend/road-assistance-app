package com.example.data.storage.repository

import android.net.Uri
import android.util.Log
import com.example.core.common.Constants
import com.example.domain.chat.model.Message
import com.example.domain.storage.repository.StorageRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageRepositoryImpl : StorageRepository {

    override suspend fun saveProfilePictureToStorage(imageUri: Uri): String {
        return try {
            val storageRef = Firebase.storage.reference
            val imageName = UUID.randomUUID().toString()
            val profilePicturesRef = storageRef.child("profile_images/$imageName")
            val profilePictureUrl =
                profilePicturesRef.putFile(imageUri).await().storage.downloadUrl.await()
            profilePictureUrl.toString()
        } catch (e: Exception) {
            Log.e(Constants.TAG, "saveProfilePictureToStorage error : ${e.message}")
            ""
        }
    }

    override suspend fun saveMessagePhotoToStorage(imageUri: Uri): String {
        return try {
            val storageRef = Firebase.storage.reference.child("chat_images/${UUID.randomUUID()}.jpg")
            val uploadTask = storageRef.putFile(imageUri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await().toString()
            downloadUrl
        } catch (e: Exception) {
            Log.e(Constants.TAG, "saveMessagePhotoToStorage error : ${e.message}")
            ""
        }
    }

}