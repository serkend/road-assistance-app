package com.example.roadAssist.presentation.screens.chat.model

import com.example.domain.chat.model.Conversation

data class ConversationModel(
    val id: String?,
    val executorId: String,
    val clientId: String,
    var lastMessage: String?,
    var lastMessageTimestamp: Long?,
    val createdAt: Long,
    var updatedAt: Long
)

fun Conversation.toModel() = ConversationModel(
    id = id,
    executorId = executorId,
    clientId = clientId,
    lastMessage = lastMessage ?: "",
    lastMessageTimestamp = lastMessageTimestamp,
    createdAt = createdAt,
    updatedAt = updatedAt
)
