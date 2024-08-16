package com.example.data.fileManager.repository
import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.core.common.ResultState
import com.example.data.fileManager.worker.DownloadImageWorker
import com.example.domain.filesManager.model.FileDownloadingResult
import com.example.domain.filesManager.repository.FileManagerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FileManagerRepositoryImpl @Inject constructor(
    private val context: Context
) : FileManagerRepository {

    override fun downloadImage(url: String?, imageName: String, position: Int): Flow<ResultState<FileDownloadingResult>> {
        return flow {
            if (url.isNullOrEmpty()) {
                emit(ResultState.Failure("Invalid URL"))
                return@flow
            }

            val inputData = Data.Builder()
                .putString("image_url", url)
                .putString("image_name", imageName)
                .build()

            val workManager = WorkManager.getInstance(context)
            val downloadRequest = OneTimeWorkRequestBuilder<DownloadImageWorker>()
                .setInputData(inputData)
                .build()

            workManager.enqueue(downloadRequest)

            val workInfoFlow = callbackFlow {
                val workInfoObserver = workManager.getWorkInfoByIdLiveData(downloadRequest.id)
                workInfoObserver.observeForever { workInfo ->
                    trySend(workInfo)
                }
                awaitClose { workInfoObserver.removeObserver { } }
            }

            workInfoFlow.collect { workInfo ->
                when (workInfo?.state) {
                    WorkInfo.State.RUNNING -> {
                        emit(ResultState.Loading())
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        val result = FileDownloadingResult(
                            name = imageName,
                            position = position
                        )
                        emit(ResultState.Success(result))
                    }
                    WorkInfo.State.FAILED -> {
                        emit(ResultState.Failure("Download image worker failed"))
                    }
                    else -> {}
                }
            }
        }
    }
}
