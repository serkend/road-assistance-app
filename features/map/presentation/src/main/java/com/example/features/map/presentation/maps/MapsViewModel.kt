package com.example.features.map.presentation.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.ResultState
import com.example.core.common.handleStateSuspended
import com.example.domain.maps.usecases.GetCurrentLocation
import com.example.domain.maps.usecases.GetRoute
import com.example.domain.requests.usecases.orders.FetchMyOrder
import com.example.domain.requests.usecases.requests.RequestsUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val requestsUseCases: RequestsUseCases,
    private val fetchMyOrder: FetchMyOrder,
    private val getRoute: GetRoute,
    private val getCurrentLocation: GetCurrentLocation
) : ViewModel() {

    private var _markersPosStateFlow: MutableStateFlow<List<LatLng>> = MutableStateFlow(emptyList())
    val markersPosStateFlow = _markersPosStateFlow.asStateFlow()

    private var _launchChooseVehicleTroubleScreen = MutableSharedFlow<Unit>()
    val launchChooseVehicleTroubleScreen = _launchChooseVehicleTroubleScreen.asSharedFlow()

    private var _markersStateFlow: MutableStateFlow<List<RequestModel>> = MutableStateFlow(emptyList())
    val markersStateFlow = _markersStateFlow.asStateFlow()

    private var _showToast = MutableSharedFlow<String>()
    val showToast = _showToast.asSharedFlow()

    private var _showRouteSharedFlow = MutableSharedFlow<List<LatLng>>()
    val showRouteSharedFlow = _showRouteSharedFlow.asSharedFlow()

    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation: StateFlow<LatLng?> = _currentLocation.asStateFlow()

    var destinationLatLng: LatLng? = null
    var hasOrder = false

    init {
//        getMyCurrentLocation()
        getRequests()
        getDestinationAndDrawRoute()
    }

    fun onRequestAssistClicked() = viewModelScope.launch {
        _launchChooseVehicleTroubleScreen.emit(Unit)
    }

    private fun getRequests() = viewModelScope.launch {
        requestsUseCases.fetchRequests().collect { result ->
            when (result) {
                is ResultState.Success -> {
                    _markersStateFlow.value = result.data?.map { it.toModel() } ?: emptyList()
                }
                is ResultState.Failure -> _showToast.emit(
                    result.e ?: "Unknown error while fetching requests"
                )
                is ResultState.Loading -> {}
                ResultState.Initial -> {}
            }
        }
    }

    fun getDestinationAndDrawRoute() = viewModelScope.launch {
        fetchMyOrder().collect {
            it.handleStateSuspended(
                onSuccess = { order ->
                    order?.requestId?.let { requestId ->
                        hasOrder = true
                        val request = requestsUseCases.getRequestById(requestId)
                        destinationLatLng = LatLng(
                            request.latitude,
                            request.longitude
                        )
                        var retries = 3
                        while (_currentLocation.value == null && retries > 0) {
                            delay(1000)
                            retries--
                        }
                        drawRoute()
                    } ?: { hasOrder = false }
                },
                onFailure = { e ->
                    _showToast.emit(e ?: "Error while fetching current user order")
                }
            )
        }
    }

    fun drawRoute() = viewModelScope.launch {
        _currentLocation.value?.let { currLocation ->
            val dest = destinationLatLng
            if (dest != null) {
                getRoute(currLocation, dest).collect { state ->
                    state.handleStateSuspended(
                        onSuccess = { path ->
                            _showRouteSharedFlow.emit(path ?: emptyList())
                        },
                        onFailure = { e ->
                            _showToast.emit(e ?: "Unknown error")
                        }
                    )
                }
            }
        } ?: _showToast.emit("Error while drawing route")
    }

    fun getMyCurrentLocation() = viewModelScope.launch {
        getCurrentLocation().collect {
            it.handleStateSuspended(
                onSuccess = { currLatLng ->
                    _currentLocation.value = LatLng(currLatLng?.latitude ?: 0.0, currLatLng?.longitude ?: 0.0)
                },
                onFailure = { e -> _showToast.emit(e ?: "Unknown error") }
            )
        }
    }

}