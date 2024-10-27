package com.example.auth.presentation.screens.auth.sign_in

import androidx.lifecycle.viewModelScope
import com.example.auth.presentation.screens.auth.sign_in.state.SignInEvent
import com.example.auth.presentation.screens.auth.sign_in.state.SignInState
import com.example.core.common.Resource
import com.example.domain.auth.model.SignInCredentials
import com.example.core.uikit.base.BaseViewModel
import com.example.domain.auth.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCases: AuthUseCases) : BaseViewModel() {

    private var _signInStateFlow: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val signInStateFlow = _signInStateFlow.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                _signInStateFlow.update { it.copy(email = event.email) }
            }
            is SignInEvent.PasswordChanged -> {
                _signInStateFlow.update { it.copy(password = event.password) }
            }
            SignInEvent.SignIn -> { signIn() }
        }
    }

    fun signIn() {
        val signInCredentials = SignInCredentials(_signInStateFlow.value.email, _signInStateFlow.value.password)
        viewModelScope.launch {
            authUseCases.signIn(signInCredentials).collect { state ->
                when (state) {
                    Resource.Loading -> {
                        _signInStateFlow.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _signInStateFlow.update { it.copy(isSignInSuccessful = true, isLoading = false) }
                    }
                    is Resource.Failure -> {
                        emitToast(state.e?.localizedMessage)
                        _signInStateFlow.update { it.copy(isLoading = false) }
//                        _showToast.emit(state.e?.localizedMessage ?: "Unknown error")
                    }
                    Resource.Initial -> {}
                }
            }
        }
    }

}