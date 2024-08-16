package com.example.chat

import com.example.data_test.repository.FakeChatRepository
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.chat.usecases.GetConversations
import com.example.domain.chat.usecases.GetMessages
import com.example.domain.chat.usecases.GetOrCreateConversation
import com.example.domain.chat.usecases.GetReceiverIdForConversation
import com.example.domain.chat.usecases.SendMessage
import org.junit.Before
import org.junit.Test

class ChatViewModelTest {

    private lateinit var chatUseCases: ChatUseCases
    private lateinit var viewModel: ChatViewModel

    @Before
    fun setUp() {
        val fakeChatRepository = FakeChatRepository()
        chatUseCases = ChatUseCases(
            getConversations = GetConversations(fakeChatRepository),
            getMessages = GetMessages(fakeChatRepository),
            sendMessage = SendMessage(fakeChatRepository),
            getOrCreateConversation = GetOrCreateConversation(fakeChatRepository),
            getReceiverIdForConversation = GetReceiverIdForConversation(fakeChatRepository)
        )
        viewModel = ChatViewModel(chatUseCases)
    }

    @Test
    fun loadMessages_withValidData_returnsFilteredMessages() {

    }

}