package com.example.features.map.presentation.requestAssistFlow.vehiclesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.ResultState
import com.example.core.common.vehicles.VehicleModel
import com.example.domain.vehicles.model.toModel
import com.example.domain.vehicles.usecases.VehiclesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseVehicleBottomSheetViewModel @Inject constructor(private val vehiclesUseCases: VehiclesUseCases) : ViewModel() {

    private val _vehiclesStateFlow = MutableStateFlow<List<VehicleModel>>(emptyList())
    val vehiclesStateFlow = _vehiclesStateFlow.asStateFlow()

    private val _showToastSharedFlow = MutableSharedFlow<String>()
    val showToastSharedFlow = _showToastSharedFlow.asSharedFlow()

    private val _isRefreshingSharedFlow = MutableSharedFlow<Boolean>()

    var launchRequestPreviewScreenSharedFlow = MutableSharedFlow<String>()

    init {
        fetchVehicles()
    }

    private fun fetchVehicles() = viewModelScope.launch {
        vehiclesUseCases.fetchVehicles().collect { state ->
            when(state) {
                is ResultState.Loading -> {
                    _isRefreshingSharedFlow.emit(true)
                    _vehiclesStateFlow.value = state.result?.map { it.toModel() } ?: emptyList()
                }
                is ResultState.Success -> {
                    _isRefreshingSharedFlow.emit(false)
                    _vehiclesStateFlow.value = state.result?.map { it.toModel() } ?: emptyList()
                }
                is ResultState.Failure -> {
                    _isRefreshingSharedFlow.emit(false)
                    _showToastSharedFlow.emit(state.e ?: "Unknown error")
                }
                ResultState.Initial -> {}
            }
        }
    }

    fun onVehicleCardClicked(vehicleId: String?) = viewModelScope.launch {
        if (vehicleId != null) {
            launchRequestPreviewScreenSharedFlow.emit(vehicleId)
        }
    }
}