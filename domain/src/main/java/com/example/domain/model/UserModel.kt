package com.example.domain.model

import android.net.Uri
import java.util.Date

data class UserModel(
    val email : String,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var image: Uri? = null,
    var userName: String = "",
    var token: String = ""
)