package com.example.domain.requests.model

import com.example.common.OrderStatus

data class Order(
    val id: String? = null,
    val status: OrderStatus,
    val executorId: String,
    val clientId: String,
    val requestId: String
)