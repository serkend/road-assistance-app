package com.example.data.common.dto

import android.net.Uri
import com.example.domain.common.User
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserDto(
    val email: String = "",
    var uId: String = "",
    @ServerTimestamp
    var createdAt: Date? = null,
    @ServerTimestamp
    var updatedAt: Date? = null,
    var image: String? = null,
    @get:PropertyName("user_name")
    @set:PropertyName("user_name")
    var userName: String = "",
    var token: String = ""
)

fun UserDto.toModel(): User = User(
    email = this.email,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    image = this.image?.let { Uri.parse(it) },
    userName = this.userName,
    token = "",
)
