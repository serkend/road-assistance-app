package com.example.domain.chat.usecases

data class ChatUseCases (
    val getConversations: GetConversations,
    val getMessages: GetMessages,
    val sendMessage: SendMessage,
    val getOrCreateConversation: GetOrCreateConversation
)