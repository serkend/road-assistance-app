package com.example.data.chat.mappers

import com.example.data.chat.dto.ConversationDto
import com.example.data.chat.dto.MessageDto
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.model.Message

fun ConversationDto.toDomain() = Conversation(
    id = id,
    executorId = executorId,
    clientId = clientId,
    lastMessage = lastMessage ?: "",
    lastMessageTimestamp = lastMessageTimestamp,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Conversation.toDto() = ConversationDto(
    id = id,
    executorId = executorId,
    clientId = clientId,
    lastMessage = lastMessage ?: "",
    lastMessageTimestamp = lastMessageTimestamp,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun MessageDto.toDomain(isOutgoing: Boolean) = Message(
    id = id,
    text = text,
    senderId = senderId,
    receiverId = receiverId,
    conversationId = conversationId,
    timestamp = timestamp,
    isOutgoing = isOutgoing
)

fun Message.toDto() = MessageDto(
    id = id,
    text = text,
    senderId = senderId,
    receiverId = receiverId,
    conversationId = conversationId,
    timestamp = timestamp
)