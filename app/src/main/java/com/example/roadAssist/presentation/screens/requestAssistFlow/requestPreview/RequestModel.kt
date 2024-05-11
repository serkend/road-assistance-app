package com.example.roadAssist.presentation.screens.requestAssistFlow.requestPreview

import android.os.Parcelable
import com.example.domain.requests.model.Request
import com.example.domain.vehicles.model.Vehicle
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.VehicleModel
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.toDomain
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.toModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestModel(
    val id: String? = null,
    val trouble: String,
    val cost: String,
    val vehicle: VehicleModel,
    val latitude: Double,
    val longitude: Double,
    val isCurrentUser: Boolean,
    val userId: String?
) : Parcelable

fun RequestModel.toDomain() = Request(
    id = id,
    trouble = trouble,
    cost = cost,
    vehicle = vehicle.toDomain(),
    latitude = latitude,
    longitude = longitude,
    userId = userId
)

fun Request.toModel() = RequestModel(
    id = id,
    trouble = trouble,
    cost = cost,
    vehicle = vehicle.toModel(),
    latitude = latitude,
    longitude = longitude,
    isCurrentUser = isCurrentUser ?: false,
    userId = userId
)