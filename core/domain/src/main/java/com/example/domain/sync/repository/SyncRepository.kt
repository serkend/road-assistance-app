package com.example.domain.sync.repository

interface SyncRepository {
    suspend fun getLastSyncTime(): Long
    suspend fun updateLastSyncTime(timestamp: Long)
    suspend fun sync()
    suspend fun shouldSync(): Boolean
}