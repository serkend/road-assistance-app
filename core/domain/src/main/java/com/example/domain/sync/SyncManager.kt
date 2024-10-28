package com.example.domain.sync

import com.example.core.common.ResultState
import com.example.domain.sync.model.SyncState
import kotlinx.coroutines.flow.Flow

interface SyncManager {
    fun schedulePeriodicSync()
    fun requestImmediateSync()
    fun cancelSync()
    fun observeSyncStatus(): Flow<ResultState<SyncState>>
}