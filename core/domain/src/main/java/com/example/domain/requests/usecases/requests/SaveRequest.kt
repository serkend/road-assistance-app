package com.example.domain.requests.usecases.requests

import com.example.core.common.ResultState
import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveRequest @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(request: Request): Flow<ResultState<Unit>> {
        return requestsRepository.saveRequest(request)
    }
}