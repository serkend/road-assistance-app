package com.example.domain.sync.usecases

import com.example.domain.requests.repository.RequestsRepository
import com.example.domain.sync.model.SyncStatus
import com.example.domain.sync.repository.SyncRepository
import com.example.domain.vehicles.repository.VehiclesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val syncRepository: SyncRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke() = flow {
        emit(SyncStatus.InProgress)
        try {
            syncRepository.sync()
            emit(SyncStatus.Success)
        } catch (e: Exception) {
            emit(SyncStatus.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(dispatcher)
}