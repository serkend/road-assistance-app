package com.example.data.requests.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.vehicles.entity.VehicleEntity

@Entity("requests_table")
class RequestEntity(
    @PrimaryKey val id: String? = null,
    val userId: String? = null,
    val trouble: String? = null,
    val cost: String? = null,
    val vehicle: VehicleEntity? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)