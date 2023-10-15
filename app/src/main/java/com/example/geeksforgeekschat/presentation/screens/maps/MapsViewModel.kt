package com.example.geeksforgeekschat.presentation.screens.maps

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() : ViewModel() {

    private var _markersPosSharedFlow: MutableStateFlow<List<LatLng>> = MutableStateFlow(emptyList())
    val markersPosSharedFlow = _markersPosSharedFlow.asStateFlow()

    fun addMarker(latLng: LatLng) {
        _markersPosSharedFlow.update { it + latLng }
    }

}