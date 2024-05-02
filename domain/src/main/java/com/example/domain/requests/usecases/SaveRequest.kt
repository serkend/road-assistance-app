package com.example.domain.requests.usecases

import com.example.domain.requests.model.Request
import com.example.domain.requests.repository.RequestsRepository
import javax.inject.Inject

class SaveRequest @Inject constructor(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(request: Request) {
        return requestsRepository.saveRequest(request)
    }
}