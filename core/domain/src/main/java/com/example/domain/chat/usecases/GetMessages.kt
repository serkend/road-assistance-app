package com.example.domain.chat.usecases

import com.example.core.common.ResultState
import com.example.domain.chat.model.Message
import com.example.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessages @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(conversationId: String): Flow<ResultState<List<Message>>> {
        return chatRepository.getMessages(conversationId)
    }
}