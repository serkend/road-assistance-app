package com.example.data.sync

import android.content.Context
import androidx.core.content.edit
import com.example.domain.sync.SyncPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SyncPreferences {

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override suspend fun getLastSyncTime(): Long = withContext(dispatcher) {
        prefs.getLong(KEY_LAST_SYNC, 0L)
    }

    override suspend fun updateLastSyncTime(timestamp: Long): Unit = withContext(dispatcher) {
        prefs.edit {
            putLong(KEY_LAST_SYNC, timestamp)
        }
    }

    override suspend fun clearSyncPrefs(): Unit = withContext(dispatcher) {
        prefs.edit { clear() }
    }

    companion object {
        private const val PREFS_NAME = "sync_preferences"
        private const val KEY_LAST_SYNC = "last_sync_timestamp"
    }

}