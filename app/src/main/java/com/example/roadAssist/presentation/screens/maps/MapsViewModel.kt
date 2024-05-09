package com.example.roadAssist.presentation.screens.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.domain.requests.usecases.RequestsUseCases
import com.example.roadAssist.presentation.screens.requestAssistFlow.requestPreview.RequestModel
import com.example.roadAssist.presentation.screens.requestAssistFlow.requestPreview.toModel
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
class MapsViewModel @Inject constructor(private val requestsUseCases: RequestsUseCases) : ViewModel() {

    private var _markersPosStateFlow: MutableStateFlow<List<LatLng>> = MutableStateFlow(emptyList())
    val markersPosStateFlow = _markersPosStateFlow.asStateFlow()

    private var _launchChooseVehicleTroubleScreen = MutableSharedFlow<Unit>()
    val launchChooseVehicleTroubleScreen = _launchChooseVehicleTroubleScreen.asSharedFlow()

    private var _markersStateFlow: MutableStateFlow<List<RequestModel>> = MutableStateFlow(emptyList())
    val markersStateFlow = _markersStateFlow.asStateFlow()

    val showToast = MutableSharedFlow<String>()

    init {
        fetchRequestsData()
    }

    fun onRequestAssistClicked() = viewModelScope.launch {
        _launchChooseVehicleTroubleScreen.emit(Unit)
    }

    private fun fetchRequestsData() = viewModelScope.launch {
        requestsUseCases.fetchRequests().collect { result ->
            when (result) {
                is ResultState.Success -> {
                    _markersStateFlow.value = result.data?.map { it.toModel() } ?: emptyList()
                }

                is ResultState.Failure -> showToast.emit(
                    result.e ?: "Unknown error while fetching requests"
                )

                is ResultState.Loading -> {}
            }
        }
    }

}