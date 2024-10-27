package com.example.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject lateinit var syncWorker: SyncWorker
    private fun setupWorkManager() {
        // Конфигурация WorkManager с поддержкой Hilt
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        // Инициализация WorkManager
        WorkManager.initialize(this, config)

        // Настройка периодической синхронизации
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES,  // Точное время выполнения
            5, TimeUnit.MINUTES    // Гибкий интервал
        )
            .setConstraints(constraints)
            .addTag("sync_work")
            .build()

        // Запуск периодической работы
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "periodic_sync",
                ExistingPeriodicWorkPolicy.KEEP, // Сохраняем существующую работу если она уже запущена
                syncRequest
            )
    }
}