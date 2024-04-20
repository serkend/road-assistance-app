package com.example.data.mappers

import com.example.data.model.UserDto
import com.example.domain.model.UserModel

fun UserModel.toDto(): UserDto = UserDto(
    email = this.email,
    uId = "",
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    image = this.image?.toString(),
    userName = this.userName,
    token = ""
)
