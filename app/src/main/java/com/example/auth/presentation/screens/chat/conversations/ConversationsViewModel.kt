package com.example.roadAssist.presentation.screens.chat.conversations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.common.User
import com.example.domain.userManager.usecases.GetCurrentUser
import com.example.domain.userManager.usecases.GetUserById
import com.example.roadAssist.presentation.screens.chat.model.ConversationModel
import com.example.roadAssist.presentation.screens.chat.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor (private val chatUseCases: ChatUseCases, private val getUserById: GetUserById, private val getCurrentUser: GetCurrentUser) : ViewModel() {
    private val _conversations = MutableStateFlow<List<ConversationModel>>(emptyList())
    val conversations = _conversations.asStateFlow()

    var user : User? = null
    init {
        loadConversations()
    }


    private fun loadConversations() {
        viewModelScope.launch {
            chatUseCases.getConversations().collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        _conversations.value = result.result?.map { conversation ->
                            val receiverId = chatUseCases.getReceiverIdForConversation(conversation.id ?: "")
                            val user = getUserById(receiverId)
                            conversation.toModel(username = user?.userName, image = user?.image)
                        } ?: emptyList()
                    }
                    is ResultState.Failure -> {
                        Log.e("TAG", "loadConversations: failure", )
                    }

                    is ResultState.Loading -> {}
                }
            }
        }
    }
}
