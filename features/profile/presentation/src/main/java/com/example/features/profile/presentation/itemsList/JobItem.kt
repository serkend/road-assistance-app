package com.example.features.profile.presentation.itemsList

import com.example.common.OrderStatus
import com.example.requestAssistFlow.vehiclesList.VehicleModel

data class JobItem(
    val id: String? = null,
    val trouble: String,
    val vehicle: VehicleModel,
    val cost: String,
    val status: OrderStatus
)
