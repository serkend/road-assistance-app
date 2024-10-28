package com.example.data.sync

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.core.common.ResultState
import com.example.data.sync.worker.SyncWorker
import com.example.domain.sync.SyncManager
import com.example.domain.sync.model.SyncState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncManagerImpl @Inject constructor(
    private val workManager: WorkManager
): SyncManager {
    override fun schedulePeriodicSync() {
        val request = createPeriodicWorkRequest()
        workManager.enqueueUniquePeriodicWork(
            PERIODIC_SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    override fun requestImmediateSync() {
        val request = createOneTimeWorkRequest()
        workManager.enqueueUniqueWork(
            IMMEDIATE_SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    override fun cancelSync() {
        workManager.cancelUniqueWork(PERIODIC_SYNC_WORK_NAME)
    }

    override fun observeSyncStatus(): Flow<ResultState<SyncState>> {
        return workManager.getWorkInfosByTagFlow(SYNC_WORKER_TAG)
            .map { workInfo ->
                workInfo.firstOrNull()?.toSyncState().let {
                    when(it) {
                        is SyncState.Error -> ResultState.Failure(e = it.message)
                        is SyncState.Idle, null -> ResultState.Initial
                        is SyncState.Running -> ResultState.Loading()
                        else -> ResultState.Success(it)
                    }
                }
            }
            .distinctUntilChanged()
    }

    private fun createPeriodicWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<SyncWorker>(
            SYNC_INTERVAL_MINUTES, TimeUnit.MINUTES,
            FLEX_INTERVAL_MINUTES, TimeUnit.MINUTES
        )
            .setConstraints(createConstraints())
            .addTag(SYNC_WORKER_TAG)
            .build()
    }

    private fun createOneTimeWorkRequest(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(createConstraints())
            .addTag(SYNC_WORKER_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }

    private fun createConstraints(): Constraints {
        return Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresBatteryNotLow(true)
            .build()
    }

    companion object {
        private const val PERIODIC_SYNC_WORK_NAME = "periodic_sync"
        private const val IMMEDIATE_SYNC_WORK_NAME = "immediate_sync"
        private const val SYNC_WORKER_TAG = "sync_worker"
        private const val SYNC_INTERVAL_MINUTES = 15L
        private const val FLEX_INTERVAL_MINUTES = 5L
    }

    private fun WorkInfo.toSyncState(): SyncState {
        return when (state) {
            WorkInfo.State.RUNNING -> SyncState.Running
            WorkInfo.State.SUCCEEDED -> SyncState.Success
            WorkInfo.State.FAILED -> SyncState.Error("Sync failed")
            WorkInfo.State.CANCELLED -> SyncState.Idle
            WorkInfo.State.ENQUEUED -> SyncState.Scheduled
            WorkInfo.State.BLOCKED -> SyncState.Waiting
        }
    }
}