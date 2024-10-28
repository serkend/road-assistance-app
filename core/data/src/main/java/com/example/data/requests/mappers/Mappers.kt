package com.example.data.requests.mappers

import com.example.data.requests.dto.OrderDto
import com.example.data.requests.dto.RequestDto
import com.example.data.requests.entity.RequestEntity
import com.example.data.vehicles.entity.toDomain
import com.example.data.vehicles.entity.toEntity
import com.example.data.vehicles.mappers.toDomain
import com.example.data.vehicles.mappers.toDto
import com.example.data.vehicles.mappers.toEntity
import com.example.domain.requests.model.Order
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

fun Request.toEntity() = RequestEntity(
    id = id ?: "",
    trouble = trouble,
    cost = cost,
    vehicle = vehicle.toEntity(this.vehicle.id ?: ""),
    latitude = latitude,
    longitude = longitude,
    userId = userId
)

fun RequestDto.toDomain() = Request(
    id = id ?: "",
    trouble = trouble ?: "",
    cost = cost ?: "",
    vehicle = vehicle?.toDomain() ?: Vehicle(),
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
    userId = userId
)

fun RequestDto.toEntity() = RequestEntity(
    id = id ?: "",
    trouble = trouble ?: "",
    cost = cost ?: "",
    vehicle = vehicle?.toEntity(),
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
    userId = userId
)

fun RequestEntity.toDomain() = Request(
    id = id ?: "",
    trouble = trouble ?: "",
    cost = cost ?: "",
    vehicle = vehicle?.toDomain() ?: Vehicle(),
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
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



