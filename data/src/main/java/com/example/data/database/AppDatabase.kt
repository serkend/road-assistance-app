package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.vehicles.dao.VehicleDao
import com.example.data.vehicles.entity.VehicleEntity

@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}