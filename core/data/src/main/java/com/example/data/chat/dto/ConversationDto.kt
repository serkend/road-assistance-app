package com.example.data.chat.dto

data class ConversationDto(
    val id: String? = null,
    val executorId: String = "",
    val clientId: String = "",
    var lastMessage: String? = null,
    var lastMessageTimestamp: Long? = null,
    val createdAt: Long = 0,
    var updatedAt: Long = 0
)
