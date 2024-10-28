package com.example.data.requests.entity

import androidx.room.TypeConverter
import com.example.data.vehicles.entity.VehicleEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RequestTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromVehicleEntity(value: VehicleEntity?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toVehicleEntity(value: String?): VehicleEntity? {
        return value?.let {
            val type = object : TypeToken<VehicleEntity>() {}.type
            gson.fromJson(it, type)
        }
    }
}