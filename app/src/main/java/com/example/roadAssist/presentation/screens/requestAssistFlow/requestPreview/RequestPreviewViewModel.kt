package com.example.roadAssist.presentation.screens.requestAssistFlow.requestPreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.vehicles.usecases.VehiclesUseCases
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.VehicleModel
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestPreviewViewModel @Inject constructor(private val vehiclesUseCases: VehiclesUseCases) :
    ViewModel() {

    val fetchedVehicleStateFlow = MutableSharedFlow<VehicleModel>()

    fun getVehicleById(vehicleId: String) = viewModelScope.launch {
        val vehicle = vehiclesUseCases.getVehicleById(vehicleId)
        fetchedVehicleStateFlow.emit(vehicle.toModel())
    }

}