package com.example.chat.model

import com.example.domain.chat.model.Message

data class MessageModel(
    val id: String? = null,
    val text: String,
    val senderId: String,
    val receiverId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isOutgoing: Boolean
)

fun Message.toModel() = MessageModel(
    id = id,
    text = text,
    senderId = senderId,
    receiverId = receiverId,
    timestamp = timestamp,
    isOutgoing = isOutgoing
)
