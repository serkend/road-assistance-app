package com.example.chat

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.ResultState
import com.example.domain.chat.usecases.ChatUseCases
import com.example.chat.model.ConversationModel
import com.example.chat.model.MessageModel
import com.example.chat.model.toModel
import com.example.domain.filesManager.model.FileDownloadingResult
import com.example.domain.filesManager.usecases.DownloadImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatUseCases: ChatUseCases, private val downloadImage: DownloadImage) : ViewModel() {

    val messages = MutableStateFlow<List<MessageModel>>(emptyList())

    val showToast = MutableSharedFlow<String>()

    private val _downloadState = MutableStateFlow<ResultState<FileDownloadingResult>>(ResultState.Initial)
    val downloadState: StateFlow<ResultState<FileDownloadingResult>> = _downloadState

    fun loadMessages(conversationId: String) = viewModelScope.launch {
        chatUseCases.getMessages(conversationId).collect { result ->
            when (result) {
                is ResultState.Success -> {
                    messages.value = result.result?.map { it.toModel() }?.sortedBy { it.timestamp } ?: emptyList()
                    Log.e("TAG", "loadMessages: ${result.result}")
                }

                is ResultState.Failure -> {
                    showToast.emit(result.e ?: "Unknown loadMessages error")
                }

                is ResultState.Loading -> {}
                ResultState.Initial -> {}
            }
        }
    }

    fun sendMessage(conversationId: String, text: String) = viewModelScope.launch {
        chatUseCases.sendMessage(conversationId, text)
    }

    fun sendPhoto(conversationId: String, imageUri: Uri) = viewModelScope.launch {
        chatUseCases.sendPhoto(conversationId, imageUri)
    }

    fun downloadPhoto(url: String?, imageName: String, position: Int) = viewModelScope.launch {
        downloadImage(url, imageName, position).collect { result ->
            _downloadState.value = result
        }
    }
}