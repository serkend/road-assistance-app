package com.example.data.vehicles.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.vehicles.model.Vehicle

@Entity(tableName = "vehicles_table")
data class VehicleEntity(
    @PrimaryKey val id: String,
    val make: String,
    val model: String,
    val year: Int
)

fun VehicleEntity.toDomain() = Vehicle(id, make, model, year)

fun Vehicle.toEntity(id: String): VehicleEntity = VehicleEntity(id = id, make, model, year)