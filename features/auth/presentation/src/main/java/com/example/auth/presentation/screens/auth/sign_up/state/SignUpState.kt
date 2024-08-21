package com.example.auth.presentation.screens.auth.sign_up.state

import android.net.Uri

data class SignUpState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false,
)