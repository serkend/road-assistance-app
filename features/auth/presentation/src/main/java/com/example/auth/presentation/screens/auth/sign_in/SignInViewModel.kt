package com.example.auth.presentation.screens.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.example.core.common.AuthState
import com.example.domain.auth.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {
    private var _loginSharedFlow: MutableSharedFlow<AuthState> = MutableSharedFlow()
    val loginSharedFlow = _loginSharedFlow.asSharedFlow()

    fun signIn(signInCredentials: SignInCredentials) {
        viewModelScope.launch {
            authUseCases.signIn(signInCredentials).collect { state ->
                when (state) {
                    Resource.Loading -> {
                        _loginSharedFlow.emit(AuthState.Loading)
                    }
                    is Resource.Success -> {
                        _loginSharedFlow.emit(AuthState.Success)
                    }
                    is Resource.Failure -> {
                        _loginSharedFlow.emit(AuthState.Failure(state.e as Exception))
//                        Log.e("TAG", "signIn: ${state.error}", )
                    }
                    Resource.Initial -> {
                        _loginSharedFlow.emit(AuthState.Initial)
                    }
                }
            }
        }
    }

}