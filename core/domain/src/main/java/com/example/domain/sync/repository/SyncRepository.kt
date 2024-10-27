package com.example.domain.sync.repository

import com.example.domain.sync.model.SyncStatus
import kotlinx.coroutines.flow.Flow

interface SyncRepository {
    suspend fun getLastSyncTime(): Long
    suspend fun updateLastSyncTime(timestamp: Long)
    suspend fun sync()
    suspend fun shouldSync(): Boolean
}