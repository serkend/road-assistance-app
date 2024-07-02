package com.example.domain.chat.usecases

import com.example.domain.chat.repository.ChatRepository
import javax.inject.Inject

class GetReceiverIdForConversation @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(conversationId: String): String {
        return chatRepository.getReceiverIdForConversation(conversationId)
    }
}