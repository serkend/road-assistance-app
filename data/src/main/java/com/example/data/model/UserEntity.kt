package com.example.data.model

import android.net.Uri
import com.example.domain.model.UserModel
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserEntity(
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

fun UserEntity.toModel(): UserModel = UserModel(
    email = this.email,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    image = this.image?.let { Uri.parse(it) },
    userName = this.userName,
    token = "",
)