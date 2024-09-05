package com.example.splash

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.handleStateSuspended
import com.example.core.uikit.base.BaseViewModel
import com.example.domain.auth.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authUseCases: AuthUseCases) : BaseViewModel() {

    private var _isUserAuthenticatedStateFlow = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticatedStateFlow = _isUserAuthenticatedStateFlow.asStateFlow()

    init {
        isUserAuthenticated()
    }

    private fun isUserAuthenticated() = viewModelScope.launch {
        authUseCases.isAuthenticated().collect { state ->
            state.handleStateSuspended(
                onSuccess = {
                    _isUserAuthenticatedStateFlow.value = it
                },
                onFailure = { e ->
                    _showToast.emit(e ?: "Unknown error")
                }
            )
        }
    }

}