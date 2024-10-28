package com.example.data.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(SYNC_CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    SYNC_CHANNEL_ID,
                    "Sync Notifications",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Notifications for sync operations"
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun showSyncNotification() {
        createNotificationChannelIfNeeded()

        val notification = NotificationCompat.Builder(context, SYNC_CHANNEL_ID)
            .setContentTitle("Синхронизация") // TODO extract to res
            .setContentText("Выполняется синхронизация данных...")
            .setSmallIcon(com.example.core.common.R.drawable.ic_sync)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .build()

        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val SYNC_CHANNEL_ID = "sync_channel"
        private const val NOTIFICATION_ID = 1
    }
}
