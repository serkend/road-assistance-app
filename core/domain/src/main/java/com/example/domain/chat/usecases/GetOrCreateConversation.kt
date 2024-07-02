package com.example.domain.chat.usecases

import com.example.domain.chat.repository.ChatRepository
import javax.inject.Inject

class GetOrCreateConversation @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(requestId: String): String {
        return chatRepository.getOrCreateConversation(requestId)
    }
}