package com.example.geeksforgeekschat.presentation.screens.user.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geeksforgeekschat.presentation.model.LoginState
import com.example.domain.usecases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {
    private var _loginStateFlow: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Initial)
    val loginStateFlow = _loginStateFlow.asStateFlow()

    private var _currentUser: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Loading)
    val currentUser = _loginStateFlow.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOut()
        }
    }

    fun isUserAuthenticated() = authUseCases.isAuthenticated(viewModelScope)

}