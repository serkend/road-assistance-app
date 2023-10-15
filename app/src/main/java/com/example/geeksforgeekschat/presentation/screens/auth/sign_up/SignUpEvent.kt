package com.example.geeksforgeekschat.presentation.screens.auth.sign_up

import android.net.Uri

sealed class SignUpEvent {
    data class EmailChanged(val email : String) : SignUpEvent()
    data class PasswordChanged(val password : String) : SignUpEvent()
    data class AvatarChanged(val avatarUri : Uri?) : SignUpEvent()
}
