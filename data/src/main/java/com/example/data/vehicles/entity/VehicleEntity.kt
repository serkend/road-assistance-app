package com.example.data.vehicles.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles_table")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val make: String,
    val model: String,
    val year: Int
)