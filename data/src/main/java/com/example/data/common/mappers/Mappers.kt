package com.example.data.common.mappers

import com.example.data.common.dto.UserDto
import com.example.domain.common.User

fun User.toDto(): UserDto = UserDto(
    email = this.email,
    uId = "",
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    image = this.image?.toString(),
    userName = this.userName,
    token = ""
)
