package com.example.data.requests.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.vehicles.entity.VehicleEntity

@Entity("requests_table")
@TypeConverters(RequestTypeConverter::class)
class RequestEntity(
    @PrimaryKey val id: String = "",
    val userId: String? = null,
    val trouble: String? = null,
    val cost: String? = null,
    val vehicle: VehicleEntity? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)