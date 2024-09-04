package com.example.core.uikit.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
open class BaseViewModel : ViewModel() {
    protected var _showToast = MutableSharedFlow<String>()
    val showToast = _showToast.asSharedFlow()
}