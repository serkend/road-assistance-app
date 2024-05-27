package com.example.domain.common

import android.net.Uri
import java.util.Date

data class User(
    val email : String,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var image: Uri? = null,
    var userName: String = "",
    var token: String = "",
    val id: String? = null
)