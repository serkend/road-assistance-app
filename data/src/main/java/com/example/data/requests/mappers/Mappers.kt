package com.example.data.requests.mappers

import com.example.data.requests.dto.RequestDto
import com.example.data.vehicles.mappers.toDto
import com.example.domain.requests.model.Request
import com.example.domain.vehicles.model.Vehicle

fun Request.toDto(userId: String) = RequestDto(
    id = id,
    trouble = trouble,
    cost = cost,
    vehicle = vehicle.toDto(),
    latitude = latitude,
    longitude = longitude,
    userId = userId
)

