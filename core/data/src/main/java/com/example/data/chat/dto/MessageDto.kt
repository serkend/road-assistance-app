package com.example.data.chat.dto

data class MessageDto(
    val id: String = "",
    val text: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val conversationId: String = "",
    val timestamp: Long = 0,
    val imageUrl: String? = null
)