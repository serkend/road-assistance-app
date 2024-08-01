package com.example.domain.requests.usecases.orders

import com.example.core.common.ResultState
import com.example.domain.requests.model.Order
import com.example.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMyAllOrders @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke() : Flow<ResultState<List<Order>>> {
        return requestsRepository.fetchMyAllOrders()
    }
}