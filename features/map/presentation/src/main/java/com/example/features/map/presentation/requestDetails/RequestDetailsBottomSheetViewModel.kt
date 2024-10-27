package com.example.features.map.presentation.requestDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.handleState
import com.example.core.uikit.base.BaseViewModel
import com.example.domain.chat.usecases.ChatUseCases
import com.example.domain.requests.usecases.requests.RequestsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsBottomSheetViewModel @Inject constructor(
    private val requestsUseCases: RequestsUseCases,
    private val chatUseCases: ChatUseCases
) : BaseViewModel() {

    val requestAcceptedSharedFlow = MutableSharedFlow<Unit>()

    val conversationIdSharedFlow = MutableSharedFlow<String>()

    fun acceptRequest(requestId: String) = viewModelScope.launch {
        requestsUseCases.acceptRequest(requestId).collect { state ->
            state.handleState(
                onSuccess = {
                    emitToast("Request was accepted")
                    requestAcceptedSharedFlow.emit(Unit)
                },
                onFailure = {
                    emitToast(it)
                }
            )
        }
        requestAcceptedSharedFlow.emit(Unit)
    }

    fun getOrCreateConversation(requestId: String) = viewModelScope.launch {
        val conversationId = chatUseCases.getOrCreateConversation(requestId)
        conversationIdSharedFlow.emit(conversationId)
    }

}