package com.example.features.profile.presentation.itemsList

import com.example.core.common.OrderStatus
import com.example.core.common.vehicles.VehicleModel

data class JobItem(
    val id: String? = null,
    val trouble: String,
    val vehicle: VehicleModel,
    val cost: String,
    val status: OrderStatus
)
