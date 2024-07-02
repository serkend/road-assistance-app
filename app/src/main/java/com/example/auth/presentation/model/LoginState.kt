package com.example.roadAssist.presentation.model

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Failure(val error : Exception) : LoginState()
}
