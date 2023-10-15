package com.example.data.mappers

import com.example.data.model.UserEntity
import com.example.domain.model.UserModel

fun UserModel.toEntity() : UserEntity =
    UserEntity(
        email = this.email,
        uId = "",
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        image = this.image?.toString(),
        userName = this.userName,
        token = ""
    )
