package com.example.domain.requests.usecases.requests

import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import javax.inject.Inject

class DeleteRequest @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(request: Request) {
        return requestsRepository.deleteRequest(request)
    }
}