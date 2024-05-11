package com.example.roadAssist.presentation.screens.itemsList

import com.example.common.OrderStatus
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.VehicleModel

data class JobItem(
    val id: String? = null,
    val trouble: String,
    val vehicle: VehicleModel,
    val cost: String,
    val status: OrderStatus
)
