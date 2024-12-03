package com.example.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.common.handleState
import com.example.core.uikit.base.BaseViewModel
import com.example.domain.auth.usecases.auth.AuthUseCases
import com.example.domain.sync.usecases.SyncUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val syncUseCases: SyncUseCases
) : BaseViewModel() {

    private var _isUserAuthenticatedStateFlow = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticatedStateFlow = _isUserAuthenticatedStateFlow.asStateFlow()

    init {
        isUserAuthenticated()
    }

    private fun isUserAuthenticated() = viewModelScope.launch {
        authUseCases.isAuthenticated().collect { state ->
            state.handleState(
                onSuccess = { isAuthenticated ->
                    _isUserAuthenticatedStateFlow.value = isAuthenticated
                    if (isAuthenticated == true) {
                       syncUseCases.requestImmediateSyncUseCase().handleState(
                           onSuccess = {
//                               Log.e("TAG", "success ")
                           },
                           onFailure = { e ->
                               emitToast(e)
                           }
                       )
                    }
                },
                onFailure = { e ->
                    emitToast(e)
                }
            )
        }
    }
}