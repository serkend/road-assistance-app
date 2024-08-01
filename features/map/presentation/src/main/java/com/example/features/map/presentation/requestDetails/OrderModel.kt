package com.example.features.map.presentation.requestDetails

import com.example.core.common.OrderStatus

class OrderModel(
    val id: String? = null,
    val status: OrderStatus,
    val executorId: String,
    val clientId: String,
    val requestId: String
)