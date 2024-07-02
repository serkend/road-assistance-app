package com.example.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.domain.chat.usecases.ChatUseCases
import com.example.chat.model.ConversationModel
import com.example.chat.model.MessageModel
import com.example.chat.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    val conversations = MutableStateFlow<List<ConversationModel>>(emptyList())
    val messages = MutableStateFlow<List<MessageModel>>(emptyList())

    val showToast = MutableSharedFlow<String>()

    fun loadMessages(conversationId: String) = viewModelScope.launch {
        chatUseCases.getMessages(conversationId).collect { result ->
            when (result) {
                is ResultState.Success -> {
                    messages.value = result.result?.map { it.toModel() }?.sortedBy { it.timestamp }
                            ?: emptyList()
                    Log.e("TAG", "loadMessages: ${result.result}")
                }

                is ResultState.Failure -> {
                    showToast.emit(result.e ?: "Unknown loadMessages error")
                }

                is ResultState.Loading -> {}
            }
        }
    }

    fun sendMessage(conversationId: String, text: String) = viewModelScope.launch {
        chatUseCases.sendMessage(conversationId, text)
    }
}