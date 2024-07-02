package com.example.domain.requests.usecases.requests

import com.example.common.ResultState
import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMyAllRequests @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke() : Flow<ResultState<List<Request>>> {
        return requestsRepository.fetchMyAllRequests()
    }
}