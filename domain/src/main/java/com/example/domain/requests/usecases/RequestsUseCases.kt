package com.example.domain.requests.usecases

data class RequestsUseCases(
    val fetchRequests: FetchRequests,
    val saveRequest: SaveRequest,
    val deleteRequest: DeleteRequest,
    val getRequestById: GetRequestById
)