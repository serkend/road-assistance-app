package com.example.features.map.presentation.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.ResultState
import com.example.domain.requests.usecases.orders.FetchMyOrder
import com.example.domain.requests.usecases.requests.RequestsUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val requestsUseCases: RequestsUseCases, private val fetchMyOrder: FetchMyOrder) : ViewModel() {

    private var _markersPosStateFlow: MutableStateFlow<List<LatLng>> = MutableStateFlow(emptyList())
    val markersPosStateFlow = _markersPosStateFlow.asStateFlow()

    private var _launchChooseVehicleTroubleScreen = MutableSharedFlow<Unit>()
    val launchChooseVehicleTroubleScreen = _launchChooseVehicleTroubleScreen.asSharedFlow()

    private var _markersStateFlow: MutableStateFlow<List<RequestModel>> = MutableStateFlow(emptyList())
    val markersStateFlow = _markersStateFlow.asStateFlow()

    val showToast = MutableSharedFlow<String>()

    val showRouteSharedFlow = MutableSharedFlow<LatLng>()

    var hasOrder = false

    init {
        fetchRequestsData()
        fetchCurrentUserOrder()
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

    fun fetchCurrentUserOrder() = viewModelScope.launch {
        fetchMyOrder().collect {
            when(it) {
                is ResultState.Success -> {
                    it.result?.requestId?.let { requestId ->
                        hasOrder = true
                        val request = requestsUseCases.getRequestById(requestId)
                        showRouteSharedFlow.emit(
                            LatLng(
                                request.latitude,
                                request.longitude
                            )
                        )
                    } ?: { hasOrder = false }
                }
                is ResultState.Failure -> {
                    hasOrder = false
                    showToast.emit(it.e ?: "Error while fetching current user order")
                }
                is ResultState.Loading -> {

                }
            }
        }
    }



}