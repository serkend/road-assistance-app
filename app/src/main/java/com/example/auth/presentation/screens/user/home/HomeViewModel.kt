package com.example.roadAssist.presentation.screens.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadAssist.presentation.model.LoginState
import com.example.domain.auth.usecases.auth.AuthUseCases
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

    fun signOut() {
        viewModelScope.launch {
            authUseCases.signOut()
        }
    }

    fun isUserAuthenticated() = authUseCases.isAuthenticated(viewModelScope)

}