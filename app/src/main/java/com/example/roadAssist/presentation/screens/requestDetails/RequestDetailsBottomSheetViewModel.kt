package com.example.roadAssist.presentation.screens.requestDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.requests.repository.RequestsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsBottomSheetViewModel @Inject constructor(private val requestsRepository: RequestsRepository) :
    ViewModel() {

    val requestAcceptedSharedFlow = MutableSharedFlow<Unit>()
    val showToast = MutableSharedFlow<String>()

    fun acceptRequest(requestId: String) = viewModelScope.launch {
        try {
            requestsRepository.acceptRequest(requestId)
            requestAcceptedSharedFlow.emit(Unit)
            showToast.emit("Request was accepted")
        } catch (e: Exception) {
            Log.e("TAG", "acceptRequest failed")
        }
    }

}