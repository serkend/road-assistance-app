package com.example.roadAssist.presentation.screens.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() : ViewModel() {

    private var _markersPosStateFlow: MutableStateFlow<List<LatLng>> = MutableStateFlow(emptyList())
    val markersPosStateFlow = _markersPosStateFlow.asStateFlow()

    private var _launchChooseVehicleTroubleScreen = MutableSharedFlow<Unit>()
    val launchChooseVehicleTroubleScreen = _launchChooseVehicleTroubleScreen.asSharedFlow()

    fun addMarker(latLng: LatLng) {
        _markersPosStateFlow.update { it + latLng }
    }

    fun onRequestAssistClicked() = viewModelScope.launch {
        _launchChooseVehicleTroubleScreen.emit(Unit)
    }

}