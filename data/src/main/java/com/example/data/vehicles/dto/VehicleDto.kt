package com.example.data.vehicles.dto

import com.example.data.vehicles.entity.VehicleEntity
import com.google.firebase.firestore.PropertyName

data class VehicleDto(
    @PropertyName("id") val id: String,
    @PropertyName("make") val make: String,
    @PropertyName("model") val model: String,
    @PropertyName("year") val year: Int
) {
    fun toEntity(): VehicleEntity = VehicleEntity(id, make, model, year)

    companion object {
        const val FIREBASE_VEHICLES = "vehicles"
        const val FIREBASE_MAKE = "make"
        const val FIREBASE_MODEL = "model"
        const val FIREBASE_YEAR = "year"
    }
}