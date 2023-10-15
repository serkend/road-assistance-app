package com.example.geeksforgeekschat.presentation.screens.auth.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.model.SignInCredentials
import com.example.geeksforgeekschat.presentation.model.LoginState
import com.example.domain.usecases.auth.AuthUseCases
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {
    private var _loginSharedFlow: MutableSharedFlow<LoginState> = MutableSharedFlow()
    val loginSharedFlow = _loginSharedFlow.asSharedFlow()

    init {

    }

    fun signIn(signInCredentials: SignInCredentials) {
        viewModelScope.launch {
            authUseCases.signIn(signInCredentials).collect { state ->
                when (state) {
                    Resource.Loading -> {
                        _loginSharedFlow.emit(LoginState.Loading)
                    }
                    is Resource.Success -> {
                        _loginSharedFlow.emit(LoginState.Success)
                    }
                    is Resource.Failure -> {
                        _loginSharedFlow.emit(LoginState.Failure(state.e as Exception))
//                        Log.e("TAG", "signIn: ${state.error}", )
                    }
                    Resource.Initial -> {
                        _loginSharedFlow.emit(LoginState.Initial)
                    }
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOut()
        }
    }

    fun isUserAuthenticated() = authUseCases.isAuthenticated(viewModelScope)

}