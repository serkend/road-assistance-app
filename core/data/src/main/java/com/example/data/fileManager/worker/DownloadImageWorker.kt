package com.example.data.filesManager

import android.content.Context
import android.os.Environment
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

class DownloadImageWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val imageUrl = inputData.getString("image_url") ?: return Result.failure()
        val imageName = inputData.getString("image_name") ?: "downloaded_image.jpg"

        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection()
                connection.connect()
                val input: InputStream = connection.getInputStream()
                val directory = File(
                    applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "ChatImages"
                )
                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val file = File(directory, imageName)
                val output = FileOutputStream(file)

                input.use { inputStream ->
                    output.use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}