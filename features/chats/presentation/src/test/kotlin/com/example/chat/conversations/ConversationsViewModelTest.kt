package com.example.chat.conversations

import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.userManager.usecases.GetUserById

import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class ConversationsViewModelTest {

    @Inject
    lateinit var chatUseCases: ChatUseCases

    @Inject
    lateinit var getUserById: GetUserById

    private lateinit var viewModel: ConversationsViewModel

    @Before
    fun setUp() {
        viewModel = ConversationsViewModel(chatUseCases, getUserById)
    }

    @Test
    fun loadConversations_initialLoad_returnsConversations() {

    }

}