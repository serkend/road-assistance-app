package com.example.domain.requests.usecases

import com.example.common.ResultState
import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRequests @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke() : Flow<ResultState<List<Request>>> {
        return requestsRepository.fetchRequests()
    }
}