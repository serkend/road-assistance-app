package com.example.data.chat.repository

import com.example.common.ResultState
import com.example.data.chat.dto.ConversationDto
import com.example.data.chat.dto.MessageDto
import com.example.data.chat.mappers.toDomain
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.model.Message
import com.example.domain.chat.repository.ChatRepository
import com.example.domain.requests.repository.RequestsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val requestsRepository: RequestsRepository
) : ChatRepository {

    private val conversationsCollection = firestore.collection("conversations")
    private val messagesCollection = firestore.collection("messages")

    override fun getMessages(conversationId: String): Flow<ResultState<List<Message>>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            trySend(ResultState.Failure("User not logged in")).isFailure
            close()
            return@callbackFlow
        }

        val subscription = messagesCollection
            .whereEqualTo("conversationId", conversationId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(ResultState.Failure(e.message ?: "Unknown error")).isFailure
                    return@addSnapshotListener
                }
                val messages = snapshot?.toObjects(MessageDto::class.java)
                    ?.map { it.toDomain(isOutgoing = it.senderId == userId) } ?: emptyList()
                trySend(ResultState.Success(messages))
            }

        awaitClose { subscription.remove() }
    }

    override fun getConversations(): Flow<ResultState<List<Conversation>>> = callbackFlow {
        val userId = auth.currentUser?.uid ?: throw RuntimeException("User not logged in")

        val resultsMap = mutableMapOf<String, Conversation>()

        fun updateAndSendConversations() {
            trySend(ResultState.Success(resultsMap.values.toList()))
        }

        val executorSubscription = conversationsCollection
            .whereEqualTo("executorId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(ResultState.Failure(e.message ?: "Unknown error")).isFailure
                    return@addSnapshotListener
                }
                snapshot?.toObjects(ConversationDto::class.java)?.forEach {
                    it.id?.let { id -> resultsMap[id] = it.toDomain() }
                }
                updateAndSendConversations()
            }

        val clientSubscription = conversationsCollection
            .whereEqualTo("clientId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    trySend(ResultState.Failure(e.message ?: "Unknown error")).isFailure
                    return@addSnapshotListener
                }
                snapshot?.toObjects(ConversationDto::class.java)?.forEach {
                    it.id?.let { id -> resultsMap[id] = it.toDomain() }
                }
                updateAndSendConversations()
            }

        awaitClose {
            executorSubscription.remove()
            clientSubscription.remove()
        }
    }

    override suspend fun sendMessage(conversationId: String, text: String) {
        val userId = auth.currentUser?.uid ?: throw RuntimeException("User not logged in")
        val receiverId = getReceiverIdForConversation(conversationId)
        val currentTime = System.currentTimeMillis()

        val message = MessageDto(
            text = text,
            senderId = userId,
            receiverId = receiverId,
            conversationId = conversationId,
            timestamp = currentTime
        )

        val documentReference = messagesCollection.add(message).await()
        val messageId = documentReference.id
        messagesCollection.document(messageId).update("id", messageId).await()

        val updates = mapOf(
            "lastMessage" to text,
            "lastMessageTimestamp" to currentTime,
            "updatedAt" to currentTime
        )
        conversationsCollection.document(conversationId).update(updates).await()
    }

    override suspend fun getReceiverIdForConversation(conversationId: String): String {
        val userId = auth.currentUser?.uid ?: throw RuntimeException("User not logged in")
        val conversation = conversationsCollection.document(conversationId)
            .get()
            .await()
            .toObject(ConversationDto::class.java)
            ?: throw NoSuchElementException("Conversation not found")
        return if (conversation.executorId == userId) conversation.clientId else conversation.executorId
    }

    override suspend fun getOrCreateConversation(requestId: String): String {
        val executorId = auth.currentUser?.uid ?: throw RuntimeException("User not logged in")
        val clientId = requestsRepository.getRequestById(requestId).userId ?: throw Exception("Client id is null")

        val conversationQuery = firestore.collection("conversations")
            .whereEqualTo("executorId", executorId)
            .whereEqualTo("clientId", clientId)
            .get()
            .await()

        return if (conversationQuery.documents.isNotEmpty()) {
            conversationQuery.documents.first().id
        } else {
            val newConversation = ConversationDto(
                id = UUID.randomUUID().toString(),
                executorId = executorId,
                clientId = clientId,
                lastMessage = null,
                lastMessageTimestamp = null,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            firestore.collection("conversations").document(newConversation.id ?: "").set(newConversation).await()
            newConversation.id ?: ""
        }
    }
}
