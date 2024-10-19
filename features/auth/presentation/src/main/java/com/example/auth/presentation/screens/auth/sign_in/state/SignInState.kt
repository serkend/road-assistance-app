package com.example.auth.presentation.screens.auth.sign_in.state

import android.net.Uri

data class SignInState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val showToast: String? = null
)
