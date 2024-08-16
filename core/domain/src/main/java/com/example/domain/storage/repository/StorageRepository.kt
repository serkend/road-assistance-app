package com.example.domain.storage.repository

import android.net.Uri

interface StorageRepository {
    suspend fun saveProfilePictureToStorage(imageUri: Uri): String
    suspend fun saveMessagePhotoToStorage(imageUri: Uri): String

}