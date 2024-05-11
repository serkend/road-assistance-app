package com.example.roadAssist.presentation.screens.chat.conversations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.domain.chat.model.Conversation
import com.example.domain.chat.usecases.ChatUseCases
import com.example.roadAssist.presentation.screens.chat.model.ConversationModel
import com.example.roadAssist.presentation.screens.chat.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor (private val chatUseCases: ChatUseCases) : ViewModel() {
    private val _conversations = MutableStateFlow<List<ConversationModel>>(emptyList())
    val conversations = _conversations.asStateFlow()

    init {
        loadConversations()
    }

    private fun loadConversations() {
        viewModelScope.launch {
            chatUseCases.getConversations().collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        _conversations.value = result.result?.map { it.toModel() } ?: emptyList()
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
