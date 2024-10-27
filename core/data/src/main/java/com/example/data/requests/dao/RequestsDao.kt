package com.example.data.requests.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.requests.entity.RequestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(vehicle: RequestEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequests(vehicles: List<RequestEntity>)

    @Update
    suspend fun updateRequest(vehicle: RequestEntity)

    @Transaction
    suspend fun updateRequests(requests: List<RequestEntity>) {
        deleteAllRequests()
        insertRequests(requests)
    }

    @Delete
    suspend fun deleteRequest(vehicle: RequestEntity)

    @Query("DELETE FROM requests_table")
    suspend fun deleteAllRequests()

    @Query("SELECT * FROM requests_table")
    fun getAllRequests(): Flow<List<RequestEntity>>

    @Query("SELECT * FROM requests_table WHERE id = :requestId")
    fun getRequestById(requestId: String): RequestEntity
}