package com.example.domain.vehicles.model

import com.example.core.common.vehicles.VehicleModel

data class Vehicle(
    val id: String? = null,
    val make: String = "",
    val model: String = "",
    val year: Int = 0
)

fun Vehicle.toModel() = VehicleModel(id = id, make = make, model = model, year = year)
fun VehicleModel.toDomain() = Vehicle(id = id, make = make, model = model, year = year)


