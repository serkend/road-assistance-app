package com.example.requestAssistFlow.requestPreview

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.domain.location.usecases.GetCurrentLocation
import com.example.domain.requests.model.Request
import com.example.domain.requests.usecases.requests.RequestsUseCases
import com.example.domain.vehicles.usecases.VehiclesUseCases
import com.example.common.vehicles.VehicleModel
import com.example.common.vehicles.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestPreviewViewModel @Inject constructor(
    private val vehiclesUseCases: VehiclesUseCases,
    private val requestsUseCases: RequestsUseCases,
    private val getCurrentLocation: GetCurrentLocation
) : ViewModel() {

    val fetchedVehicleStateFlow = MutableStateFlow<com.example.common.vehicles.VehicleModel?>(null)
    val showToastSharedFlow = MutableSharedFlow<String>()

    private var currentLocation: Location? = null

    init {
        getUserCurrentLocation()
    }

    fun getVehicleById(vehicleId: String) = viewModelScope.launch {
        val vehicle = vehiclesUseCases.getVehicleById(vehicleId)
        fetchedVehicleStateFlow.emit(vehicle.toModel())
    }

    fun saveRequest(trouble: String, cost: String) = viewModelScope.launch {
        val fetchedVehicle = fetchedVehicleStateFlow.value
        fetchedVehicle?.let {
            requestsUseCases.saveRequest(
                Request(
                    id = null,
                    trouble = trouble,
                    cost = cost,
                    vehicle = it.toDomain(),
                    latitude = currentLocation?.latitude ?: 0.0,
                    longitude = currentLocation?.longitude ?: 0.0,
                    userId = null
                )
            )
        }
    }

    private fun getUserCurrentLocation() = viewModelScope.launch {
        getCurrentLocation().collect {
            if (it is ResultState.Success) {
                currentLocation = it.data
            } else if( it is ResultState.Failure) {
                showToastSharedFlow.emit(it.e ?: "Unknown location error")
            }
        }
    }
}