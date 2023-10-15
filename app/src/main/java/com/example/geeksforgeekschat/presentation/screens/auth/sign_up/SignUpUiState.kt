package com.example.geeksforgeekschat.presentation.screens.auth.sign_up

import android.net.Uri

data class SignUpUiState(
    val email : String = "",
    val password : String = "",
    val image : Uri? = null
)
