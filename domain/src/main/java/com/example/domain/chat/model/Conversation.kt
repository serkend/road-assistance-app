package com.example.domain.chat.model

data class Conversation(
    val id: String?,
    val executorId: String,
    val clientId: String,
    var lastMessage: String?,
    var lastMessageTimestamp: Long?,
    val createdAt: Long,
    var updatedAt: Long
)