package com.example.roadAssist.presentation.screens.requestAssistFlow.vehicleAddUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.vehicles.model.Vehicle
import com.example.domain.vehicles.usecases.VehiclesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleAddUpdateBottomSheetViewModel @Inject constructor(private val vehiclesUseCases: VehiclesUseCases) : ViewModel() {

    private var _showToastMessage = MutableSharedFlow<String>()
    val showToastMessage = _showToastMessage.asSharedFlow()

    private var _launchRequestPreviewScreenSharedFlow = MutableSharedFlow<Unit>()
    val launchRequestPreviewScreenSharedFlow = _launchRequestPreviewScreenSharedFlow.asSharedFlow()

    fun onAddVehicleClicked(make: String, model: String, year: Int) =
        viewModelScope.launch {
            try {
                vehiclesUseCases.saveVehicle(
                    vehicle = Vehicle(
                        id = null, make = make, model = model, year = year
                    )
                )
                _showToastMessage.emit("New vehicle was successfully added.")
            } catch (e :Exception) {
                e.printStackTrace()
                _showToastMessage.emit("Error while adding new vehicle happened.")
            }

        }
}