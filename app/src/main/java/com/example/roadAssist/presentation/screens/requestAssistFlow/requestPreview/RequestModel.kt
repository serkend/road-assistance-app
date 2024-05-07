package com.example.roadAssist.presentation.screens.requestAssistFlow.requestPreview

import com.example.domain.requests.model.Request
import com.example.domain.vehicles.model.Vehicle
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.VehicleModel
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.toDomain

data class RequestModel (
    val id: String? = null,
    val trouble: String,
    val cost: String,
    val vehicle: VehicleModel,
    val latitude: Double,
    val longitude: Double
)

fun RequestModel.toDomain() = Request(
    id = id,
    trouble = trouble,
    cost = "",
    vehicle = vehicle.toDomain(),
    latitude = latitude,
    longitude = longitude
)