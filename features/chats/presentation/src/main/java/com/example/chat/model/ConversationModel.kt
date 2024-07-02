package com.example.chat.model

import android.net.Uri
import com.example.domain.chat.model.Conversation

data class ConversationModel(
    val id: String?,
    val executorId: String,
    val clientId: String,
    var lastMessage: String?,
    var lastMessageTimestamp: Long?,
    val createdAt: Long,
    var updatedAt: Long,
    val companionUsername: String?,
    val companionImage: Uri?
)

fun Conversation.toModel(username:String?, image: Uri?) = ConversationModel(
    id = id,
    executorId = executorId,
    clientId = clientId,
    lastMessage = lastMessage ?: "",
    lastMessageTimestamp = lastMessageTimestamp,
    createdAt = createdAt,
    updatedAt = updatedAt,
    companionUsername = username,
    companionImage = image
)
