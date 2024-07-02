package com.example.data.requests.dto

import com.example.common.OrderStatus

data class OrderDto(
    val id: String? = null,
    val status: OrderStatus = OrderStatus.InProgress,
    val executorId: String = "",
    val clientId: String = "",
    val requestId: String = ""
) {
    companion object {
        const val FIREBASE_ORDERS = "orders"
    }
}