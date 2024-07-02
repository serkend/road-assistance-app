package com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList

import android.os.Parcelable
import com.example.domain.vehicles.model.Vehicle
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleModel(
    val id: String? = null,
    val make: String,
    val model: String,
    val year: Int
) : Parcelable

fun Vehicle.toModel() = VehicleModel(id = id, make = make, model = model, year = year)
fun VehicleModel.toDomain() = Vehicle(id = id, make = make, model = model, year = year)
