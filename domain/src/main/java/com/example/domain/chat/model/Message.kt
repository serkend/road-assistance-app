package com.example.domain.chat.model

data class Message(
    val id: String,
    val text: String,
    val senderId: String,
    val receiverId: String,
    val conversationId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isOutgoing: Boolean
)