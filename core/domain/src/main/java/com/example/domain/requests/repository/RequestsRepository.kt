package com.example.domain.requests.repository

import com.example.core.common.ResultState
import com.example.domain.requests.model.Order
import com.example.domain.requests.model.Request
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {
    suspend fun fetchRequests(): Flow<ResultState<List<Request>>>
    suspend fun getRequestById(requestId: String): Request
    suspend fun saveRequest(request: Request): Flow<ResultState<Unit>>
    suspend fun deleteRequest(request: Request)

    suspend fun saveOrder(order: Order)
    suspend fun acceptRequest(requestId: String): Flow<ResultState<Unit>>

    suspend fun fetchCurrentUserOrder(): Flow<ResultState<Order>>
    suspend fun fetchMyAllOrders(): Flow<ResultState<List<Order>>>
    suspend fun fetchMyAllRequests(): Flow<ResultState<List<Request>>>

}