package com.example.data.userManager.mappers

import android.net.Uri
import com.example.data.userManager.dto.UserDto
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

fun UserDto.toModel(): User = User(
    email = this.email,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    image = this.image?.let { Uri.parse(it) },
    userName = this.userName,
    token = "",
    id = uId
)