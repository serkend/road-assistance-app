package com.example.domain.sync

interface SyncPreferences {
    suspend fun getLastSyncTime(): Long
    suspend fun updateLastSyncTime(timestamp: Long)
    suspend fun clearSyncPrefs()
}