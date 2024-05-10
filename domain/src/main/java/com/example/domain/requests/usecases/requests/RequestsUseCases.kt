package com.example.domain.requests.usecases.requests

data class RequestsUseCases(
    val fetchRequests: FetchRequests,
    val saveRequest: SaveRequest,
    val deleteRequest: DeleteRequest,
    val getRequestById: GetRequestById,
    val acceptRequest: AcceptRequest
)