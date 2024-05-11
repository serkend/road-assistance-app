package com.example.domain.chat.usecases

import com.example.common.ResultState
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrCreateConversation @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(requestId: String): String {
        return chatRepository.getOrCreateConversation(requestId)
    }
}