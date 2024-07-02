package com.example.domain.requests.usecases.requests

import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import javax.inject.Inject

class GetRequestById @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(requestId: String) : Request {
        return requestsRepository.getRequestById(requestId)
    }
}