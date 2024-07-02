package com.example.domain.requests.usecases.requests

import com.example.domain.requests.repository.RequestsRepository
import javax.inject.Inject

class AcceptRequest @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(requestId: String) {
        return requestsRepository.acceptRequest(requestId)
    }
}