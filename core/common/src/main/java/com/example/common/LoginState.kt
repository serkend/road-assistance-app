package com.example.common

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Failure(val error : Exception) : LoginState()
}
