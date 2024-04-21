package com.example.domain.auth.model

import android.net.Uri

data class SignUpCredentials(
    val email: String , val password: String , val imageUri: Uri?
)