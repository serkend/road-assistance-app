package com.example.chat

import app.cash.turbine.test
import com.example.core.common.testing.CoroutineTestRule
import com.example.data_test.repository.FakeChatRepository
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.chat.usecases.GetConversations
import com.example.domain.chat.usecases.GetMessages
import com.example.domain.chat.usecases.GetOrCreateConversation
import com.example.domain.chat.usecases.GetReceiverIdForConversation
import com.example.domain.chat.usecases.SendMessage
import com.example.domain.chat.usecases.SendPhoto
import com.google.common.truth.Truth
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChatViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
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
            getReceiverIdForConversation = GetReceiverIdForConversation(fakeChatRepository),
            sendPhoto = SendPhoto(fakeChatRepository)
        )
        viewModel = ChatViewModel(chatUseCases, mockk())
    }

    @Test
    fun loadMessages_emptyConversationsList_emitsSuccessEmpty() = runTest {
        viewModel.loadMessages("conv1")

        viewModel.messages.test {
            val initial = awaitItem()
            Truth.assertThat(initial).isEmpty()
        }
    }

    @Test
    fun loadMessages_notEmptyConversationList_emitsSuccess() = runTest {
        viewModel.loadMessages("conv1")

        viewModel.messages.test {
            awaitItem()

            val loadedMessages = awaitItem()
            Truth.assertThat(loadedMessages).isNotEmpty()
            Truth.assertThat(loadedMessages.size).isEqualTo(2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun sendMessage_newMessage_emitsSuccess() = runTest {
        val conversationId = "conv1"
        val messageText = "New message"

        viewModel.messages.test {
            viewModel.loadMessages(conversationId)
            awaitItem()

            var loadedMessages = awaitItem()
            Truth.assertThat(loadedMessages).hasSize(2)

            viewModel.sendMessage(conversationId, messageText)
            viewModel.loadMessages(conversationId)

            loadedMessages = awaitItem()
            Truth.assertThat(loadedMessages).hasSize(3)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should send correct message text`() = runTest {
        val conversationId = "conv1"
        val messageText = "New message"

        viewModel.sendMessage(conversationId, messageText)

        viewModel.messages.test {
            val initialState = awaitItem()
            Truth.assertThat(initialState).isEmpty()

            viewModel.loadMessages(conversationId)

            val state = awaitItem()
            Truth.assertThat(state.last().text).isEqualTo(messageText)
        }
    }

}