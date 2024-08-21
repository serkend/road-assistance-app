package com.example.auth.presentation.screens.auth.sign_up.state

import android.net.Uri

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class UsernameChanged(val username: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class PickPhoto(val uri: Uri?) : SignUpEvent()
    object SignUp : SignUpEvent()
}
