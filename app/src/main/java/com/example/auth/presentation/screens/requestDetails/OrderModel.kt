package com.example.roadAssist.presentation.screens.requestDetails

import com.example.common.OrderStatus

class OrderModel(
    val id: String? = null,
    val status: OrderStatus,
    val executorId: String,
    val clientId: String,
    val requestId: String
)