package com.example.data.requests.mappers

import com.example.data.requests.dto.OrderDto
import com.example.data.requests.dto.RequestDto
import com.example.data.vehicles.mappers.toDto
import com.example.domain.requests.model.Order
import com.example.domain.requests.model.Request

fun Request.toDto(userId: String) = RequestDto(
    id = id,
    trouble = trouble,
    cost = cost,
    vehicle = vehicle.toDto(),
    latitude = latitude,
    longitude = longitude,
    userId = userId
)

fun Order.toDto(userId: String) = OrderDto(
    id = id,
    status = status,
    executorId = userId,
    clientId = clientId,
    requestId = requestId
)

fun OrderDto.toDomain() = Order(
    id = id,
    status = status,
    executorId = executorId,
    clientId = clientId,
    requestId = requestId
)


