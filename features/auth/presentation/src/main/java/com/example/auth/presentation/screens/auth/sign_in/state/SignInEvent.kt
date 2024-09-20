package com.example.auth.presentation.screens.auth.sign_in.state

import android.net.Uri

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    object SignIn : SignInEvent()
}
