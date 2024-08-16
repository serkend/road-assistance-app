package com.example.data_test.repository

import android.net.Uri
import com.example.core.common.ResultState
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.model.Message
import com.example.domain.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
class FakeChatRepository : ChatRepository {


    private var currentUserId: String? = "user1"

    override fun getMessages(conversationId: String): Flow<ResultState<List<Message>>> = flow {
        val userId = currentUserId
        if (userId == null) {
            emit(ResultState.Failure("User not logged in"))
            return@flow
        }

        emit(ResultState.Success(messages[conversationId] ?: emptyList()))
    }

    override fun getConversations(): Flow<ResultState<List<Conversation>>> = flow {
        val userId = currentUserId ?: throw RuntimeException("User not logged in")
        val userConversations = conversations.filter {
            it.executorId == userId || it.clientId == userId
        }
        emit(ResultState.Success(userConversations))
    }

    override suspend fun sendMessage(conversationId: String, text: String) {
        val userId = currentUserId ?: throw RuntimeException("User not logged in")
        val receiverId = getReceiverIdForConversation(conversationId)
        val currentTime = System.currentTimeMillis()

        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            senderId = userId,
            receiverId = receiverId,
            conversationId = conversationId,
            timestamp = currentTime,
            isOutgoing = true
        )

        messages.getOrPut(conversationId) { mutableListOf() }.add(message)

        conversations.find { it.id == conversationId }?.apply {
            lastMessage = text
            lastMessageTimestamp = currentTime
            updatedAt = currentTime
        }
    }

    override suspend fun sendPhoto(conversationId: String, imageUri: Uri) {
        TODO("Not yet implemented")
    }

    override suspend fun getReceiverIdForConversation(conversationId: String): String {
        val userId = currentUserId ?: throw RuntimeException("User not logged in")
        val conversation = conversations.find { it.id == conversationId } ?: throw NoSuchElementException("Conversation not found")

        return if (conversation.executorId == userId) conversation.clientId else conversation.executorId
    }

    override suspend fun getOrCreateConversation(requestId: String): String {
        val executorId = currentUserId ?: throw RuntimeException("User not logged in")
        val clientId = "testClientId"

        val existingConversation = conversations.firstOrNull {
            it.executorId == executorId && it.clientId == clientId
        }

        return existingConversation?.id ?: run {
            val newConversation = Conversation(
                id = UUID.randomUUID().toString(),
                executorId = executorId,
                clientId = clientId,
                lastMessage = null,
                lastMessageTimestamp = null,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            conversations.add(newConversation)
            newConversation.id!!
        }
    }

    companion object {
        private val messages = mutableMapOf(
            "conv1" to mutableListOf(
                Message(
                    id = "msg1",
                    text = "Hello",
                    senderId = "user1",
                    receiverId = "user2",
                    conversationId = "conv1",
                    timestamp = System.currentTimeMillis(),
                    isOutgoing = true
                ),
                Message(
                    id = "msg2",
                    text = "Hello!",
                    senderId = "user2",
                    receiverId = "user1",
                    conversationId = "conv1",
                    timestamp = System.currentTimeMillis(),
                    isOutgoing = false
                )
            ),
            "conv2" to mutableListOf(
                Message(
                    id = "msg3",
                    text = "Hi",
                    senderId = "user2",
                    receiverId = "user1",
                    conversationId = "conv2",
                    timestamp = System.currentTimeMillis(),
                    isOutgoing = true
                ),
                Message(
                    id = "msg4",
                    text = "Hello",
                    senderId = "user1",
                    receiverId = "user2",
                    conversationId = "conv2",
                    timestamp = System.currentTimeMillis(),
                    isOutgoing = false
                )
            )
        )
        val conversations = mutableListOf(
            Conversation(
                id = "conv1",
                executorId = "user1",
                clientId = "user2",
                lastMessage = "Hello",
                lastMessageTimestamp = System.currentTimeMillis(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            ),
            Conversation(
                id = "conv2",
                executorId = "user2",
                clientId = "user1",
                lastMessage = "Hi",
                lastMessageTimestamp = System.currentTimeMillis(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}
