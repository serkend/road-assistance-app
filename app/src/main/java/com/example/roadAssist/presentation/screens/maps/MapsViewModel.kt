package com.example.roadAssist.presentation.screens.maps

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() : ViewModel() {

    private var _markersPosStateFlow: MutableStateFlow<List<LatLng>> = MutableStateFlow(emptyList())
    val markersPosStateFlow = _markersPosStateFlow.asStateFlow()

    fun addMarker(latLng: LatLng) {
        _markersPosStateFlow.update { it + latLng }
    }

    fun onRequestAssistClicked() {

    }

}