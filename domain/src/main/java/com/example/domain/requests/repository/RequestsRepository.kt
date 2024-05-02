package com.example.domain.requests.repository

import com.example.common.ResultState
import com.example.domain.requests.model.Request
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {
    suspend fun fetchRequests(): Flow<ResultState<List<Request>>>
    suspend fun getRequestById(requestId: Long): Request
    suspend fun saveRequest(request: Request)
    suspend fun deleteRequest(request: Request)
}