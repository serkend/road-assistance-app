package com.example.domain.chat.usecases

import com.example.domain.chat.repository.ChatRepository
import javax.inject.Inject

class SendMessage @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(conversationId: String, text: String) {
        return chatRepository.sendMessage(conversationId, text)
    }
}