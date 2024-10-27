package com.example.domain.sync.model

sealed class SyncStatus {
    object Idle : SyncStatus()
    object InProgress : SyncStatus()
    object Success : SyncStatus()
    data class Error(val message: String) : SyncStatus()
}