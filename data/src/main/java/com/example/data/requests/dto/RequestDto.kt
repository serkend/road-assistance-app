package com.example.data.requests.dto

import com.example.data.vehicles.dto.VehicleDto
import com.example.data.vehicles.mappers.toDomain
import com.example.domain.requests.model.Request
import com.example.domain.vehicles.model.Vehicle
import com.google.firebase.firestore.PropertyName

data class RequestDto(
    @PropertyName("id") val id: String? = null,
    @PropertyName("userId") val userId: String? = null,
    @PropertyName("trouble") val trouble: String? = null,
    @PropertyName("cost") val cost: String? = null,
    @PropertyName("vehicle") val vehicle: VehicleDto? = null,
    @PropertyName("latitude") val latitude: Double? = null,
    @PropertyName("longitude") val longitude: Double? = null
) {

    fun toDomain() = Request(
        id = id ?: "",
        trouble = trouble ?: "",
        cost = cost ?: "",
        vehicle = vehicle?.toDomain() ?: Vehicle(),  // Ensure Vehicle also has a suitable default constructor or handle it being null appropriately
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0
    )

    companion object {
        const val FIREBASE_REQUESTS = "requests"
    }
}