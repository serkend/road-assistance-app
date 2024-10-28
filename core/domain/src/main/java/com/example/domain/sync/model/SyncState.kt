package com.example.domain.sync.model

sealed class SyncState {
    object Idle : SyncState()
    object Running : SyncState()
    object Success : SyncState()
    object Scheduled : SyncState()
    object Waiting : SyncState()
    data class Error(val message: String) : SyncState()
}