package com.example.domain.storage.usecases

import android.net.Uri
import com.example.domain.storage.repository.StorageRepository
import javax.inject.Inject

class SaveProfilePictureToStorage @Inject constructor(private val storageRepository: StorageRepository) {
    suspend operator fun invoke(imageUri: Uri): String {
        return storageRepository.saveProfilePictureToStorage(imageUri)
    }
}