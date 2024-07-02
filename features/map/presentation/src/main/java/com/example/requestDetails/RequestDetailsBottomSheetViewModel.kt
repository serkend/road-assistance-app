package com.example.requestDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.requests.usecases.requests.RequestsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsBottomSheetViewModel @Inject constructor(private val requestsUseCases: RequestsUseCases, val chatUseCases: ChatUseCases) :
    ViewModel() {

    val requestAcceptedSharedFlow = MutableSharedFlow<Unit>()
    val showToast = MutableSharedFlow<String>()

    val conversationIdSharedFlow = MutableSharedFlow<String>()

    fun acceptRequest(requestId: String) = viewModelScope.launch {
        try {
            requestsUseCases.acceptRequest(requestId)
            requestAcceptedSharedFlow.emit(Unit)
            showToast.emit("Request was accepted")
        } catch (e: Exception) {
            Log.e("TAG", "acceptRequest failed")
        }
    }

    fun getOrCreateConversation(requestId: String) = viewModelScope.launch {
        val conversationId = chatUseCases.getOrCreateConversation(requestId)
        conversationIdSharedFlow.emit(conversationId)
    }

}