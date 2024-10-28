package com.example.data.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.common.IoDispatcher
import com.example.data.notifications.NotificationHelper
import com.example.domain.sync.repository.SyncRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncRepository: SyncRepository,
    private val notificationHelper: NotificationHelper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(dispatcher) {
        try {
            withContext(Dispatchers.Main) {
                notificationHelper.showSyncNotification()
            }

            syncRepository.sync()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

}