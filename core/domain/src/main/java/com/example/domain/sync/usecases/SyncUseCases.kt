package com.example.domain.sync.usecases

import com.example.core.common.ResultState
import com.example.domain.sync.SyncManager
import com.example.domain.sync.model.SyncState
import com.example.domain.sync.repository.SyncRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class SyncUseCases(
    val schedulePeriodicSyncUseCase: SchedulePeriodicSyncUseCase,
    val requestImmediateSyncUseCase: RequestImmediateSyncUseCase,
    val observeSyncStateUseCase: ObserveSyncStateUseCase
)

class ObserveSyncStateUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    operator fun invoke(): Flow<ResultState<SyncState>> = syncManager.observeSyncStatus()
}

class SchedulePeriodicSyncUseCase @Inject constructor(
    private val syncManager: SyncManager
) {
    operator fun invoke() {
        syncManager.schedulePeriodicSync()
    }
}

class RequestImmediateSyncUseCase @Inject constructor(
    private val syncManager: SyncManager,
    private val syncRepository: SyncRepository
) {
    suspend operator fun invoke(): ResultState<Unit> =
        try {
//            if (syncRepository.shouldSync()) {
                syncManager.requestImmediateSync()
//            }
            ResultState.Success(Unit)
        } catch (e: Exception) {
            ResultState.Failure(e.localizedMessage)
        }
}