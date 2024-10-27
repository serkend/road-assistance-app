package com.example.data.sync

import com.example.domain.requests.repository.RequestsRepository
import com.example.domain.sync.SyncPreferences
import com.example.domain.sync.model.SyncStatus
import com.example.domain.sync.repository.SyncRepository
import com.example.domain.vehicles.repository.VehiclesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
    private val syncPreferences: SyncPreferences,
    private val requestRepository: RequestsRepository,
    private val vehicleRepository: VehiclesRepository,
    private val dispatcher: CoroutineDispatcher
) : SyncRepository {

    override suspend fun getLastSyncTime(): Long =
        withContext(dispatcher) {
            syncPreferences.getLastSyncTime()
        }

    override suspend fun updateLastSyncTime(timestamp: Long) {
        withContext(dispatcher) {
            syncPreferences.updateLastSyncTime(timestamp)
        }
    }

    override suspend fun shouldSync(): Boolean {
        val lastSync = getLastSyncTime()
        return System.currentTimeMillis() - lastSync >= MIN_SYNC_INTERVAL
    }

    override suspend fun sync() {
        coroutineScope {
            try {
                if (!shouldSync()) return@coroutineScope SyncStatus.Success

                awaitAll(
                    async { requestRepository.fetchRequests().first() },
                    async { vehicleRepository.fetchVehicles().first() }
                )
                updateLastSyncTime(System.currentTimeMillis())
                SyncStatus.Success
            } catch (e: Exception) {
                SyncStatus.Error(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        private const val MIN_SYNC_INTERVAL = 15 * 60 * 1000L // 15 min
    }
}