package com.example.domain.requests.model

import com.example.domain.vehicles.model.Vehicle

data class Request(
    val id: String?,
    val trouble: String,
    val cost: String,
    val vehicle: Vehicle,
    val latitude: Double,
    val longitude: Double,
    val isCurrentUser: Boolean? = null
)

