package com.example.features.map.presentation.requestAssistFlow.requestPreview

import android.os.Parcelable
import com.example.domain.requests.model.Request
import com.example.core.common.vehicles.VehicleModel
import com.example.domain.vehicles.model.toModel
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

//fun RequestModel.toDomain(): Request = Request(
//    id = id,
//    trouble = trouble,
//    cost = cost,
//    vehicle = vehicle.toDomain(),
//    latitude = latitude,
//    longitude = longitude,
//    userId = userId
//)

fun Request.toModel(): RequestModel = RequestModel(
    id = id,
    trouble = trouble,
    cost = cost,
    vehicle = vehicle.toModel(),
    latitude = latitude,
    longitude = longitude,
    isCurrentUser = isCurrentUser ?: false,
    userId = userId
)