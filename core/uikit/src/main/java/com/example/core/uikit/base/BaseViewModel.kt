package com.example.core.uikit.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
open class BaseViewModel : ViewModel() {
    private var _showToast = MutableSharedFlow<String>()
    val showToast = _showToast.asSharedFlow()

    protected suspend fun emitToast(message: String?) {
        _showToast.emit(message ?: "Unknown error")
    }
}