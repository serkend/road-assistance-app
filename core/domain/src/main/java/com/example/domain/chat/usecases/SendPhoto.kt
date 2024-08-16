package com.example.domain.chat.usecases

import android.net.Uri
import com.example.domain.chat.repository.ChatRepository
import javax.inject.Inject

class SendPhoto @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(conversationId: String, imageUri: Uri) {
        return chatRepository.sendPhoto(conversationId, imageUri)
    }
}