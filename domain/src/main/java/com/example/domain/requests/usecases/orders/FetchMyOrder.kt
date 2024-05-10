package com.example.domain.requests.usecases.orders

import com.example.common.ResultState
import com.example.domain.requests.model.Order
import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMyOrder @Inject constructor(private val requestsRepository: RequestsRepository) {
    operator fun invoke() : Flow<ResultState<Order>> {
        return requestsRepository.fetchCurrentUserOrder()
    }
}