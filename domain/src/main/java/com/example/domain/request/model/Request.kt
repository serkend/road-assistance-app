package com.example.domain.request.model

import com.example.domain.vehicles.model.Vehicle

data class Request(
    val trouble: String,
    val cost: String,
    val vehicle: Vehicle,

)
