package com.example.core.common

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Failure(val error : Exception) : AuthState()
}
