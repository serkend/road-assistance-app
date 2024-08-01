package com.example.domain.chat.usecases

import com.example.core.common.ResultState
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConversations @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(): Flow<ResultState<List<Conversation>>> {
        return chatRepository.getConversations()
    }
}