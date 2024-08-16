package com.example.chat.conversations

import com.example.chat.model.ConversationModel
import com.example.chat.model.toModel
import com.example.core.common.ResultState
import com.example.core.common.testing.CoroutineTestRule
import com.example.data_test.repository.FakeChatRepository
import com.example.data_test.repository.FakeUserManagerRepository
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.chat.usecases.GetConversations
import com.example.domain.chat.usecases.GetMessages
import com.example.domain.chat.usecases.GetOrCreateConversation
import com.example.domain.chat.usecases.GetReceiverIdForConversation
import com.example.domain.chat.usecases.SendMessage
import com.example.domain.userManager.usecases.GetUserById
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule

class ConversationsViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var chatUseCases: ChatUseCases
    private lateinit var getUserById: GetUserById

    private lateinit var viewModel: ConversationsViewModel

    @Before
    fun setUp() {
        val fakeChatRepository = FakeChatRepository()
        val fakeUserManagerRepository = FakeUserManagerRepository()
        chatUseCases = ChatUseCases(
            getConversations = GetConversations(fakeChatRepository),
            getMessages = GetMessages(fakeChatRepository),
            sendMessage = SendMessage(fakeChatRepository),
            getOrCreateConversation = GetOrCreateConversation(fakeChatRepository),
            getReceiverIdForConversation = GetReceiverIdForConversation(fakeChatRepository)
        )
        getUserById = GetUserById(fakeUserManagerRepository)
        viewModel = ConversationsViewModel(chatUseCases, getUserById)
    }

    @Test
    fun loadConversations_initialLoad_returnsConversations() = runTest {
        val actual = viewModel.conversations.value
        val expected = chatUseCases.getConversations().first().result?.map {
            it.toModel(companionName = FakeUserManagerRepository.user2Name, null)
        }
        Truth.assertThat(actual).isEqualTo(expected)
    }

}