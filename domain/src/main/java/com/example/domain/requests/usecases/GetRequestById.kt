package com.example.domain.requests.usecases

import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import javax.inject.Inject

class GetRequestById @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(requestId: Long) : Request {
        return requestsRepository.getRequestById(requestId)
    }
}