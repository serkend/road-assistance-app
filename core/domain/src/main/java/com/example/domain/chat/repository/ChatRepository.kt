package com.example.domain.chat.repository

import android.net.Uri
import com.example.core.common.ResultState
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getConversations(): Flow<ResultState<List<Conversation>>>
    fun getMessages(conversationId: String): Flow<ResultState<List<Message>>>
    suspend fun sendMessage(conversationId: String, text: String)
    suspend fun sendPhoto(conversationId: String, imageUri: Uri)
    suspend fun getOrCreateConversation(requestId: String): String
    suspend fun getReceiverIdForConversation(conversationId: String): String
}
