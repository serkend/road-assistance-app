package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.requests.dao.RequestsDao
import com.example.data.requests.entity.RequestEntity
import com.example.data.vehicles.dao.VehicleDao
import com.example.data.vehicles.entity.VehicleEntity

@Database(entities = [VehicleEntity::class, RequestEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun requestsDao(): RequestsDao
}