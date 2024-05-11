package com.example.roadAssist.presentation.screens.itemsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.OrderStatus
import com.example.common.ResultState
import com.example.domain.requests.usecases.orders.FetchMyOrder
import com.example.domain.requests.usecases.orders.OrdersUseCases
import com.example.domain.requests.usecases.requests.RequestsUseCases
import com.example.domain.vehicles.usecases.VehiclesUseCases
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.VehicleModel
import com.example.roadAssist.presentation.screens.requestAssistFlow.vehiclesList.toModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsListViewModel @Inject constructor(
    private val requestsUseCases: RequestsUseCases,
    private val ordersUseCases: OrdersUseCases,
    private val vehiclesUseCases: VehiclesUseCases
) : ViewModel() {

    val showToast = MutableSharedFlow<String>()

    val jobItemsStateFlow = MutableStateFlow<List<JobItem>>(emptyList())
    val vehiclesStateFlow = MutableStateFlow<List<VehicleModel>>(emptyList())

    fun getCurrentUserJobs() = viewModelScope.launch {
        ordersUseCases.fetchMyAllOrders().collect { state ->
            when(state) {
                is ResultState.Success -> {
                    val jobItems = state.result?.map { order ->
                        order.requestId.let { requestId ->
                            val request = requestsUseCases.getRequestById(requestId)
                            JobItem(
                                id = order.id,
                                trouble = request.trouble,
                                vehicle = request.vehicle.toModel(),
                                cost = request.cost,
                                status = order.status
                            )
                        }
                    }
                    jobItemsStateFlow.value = jobItems ?: emptyList()
                }
                is ResultState.Failure -> {
                    showToast.emit(state.e ?: "Error while fetching current user order")
                }
                is ResultState.Loading -> {}
            }
        }
    }

    fun fetchCurrentUserRequests() = viewModelScope.launch {
        val ordersFlow = ordersUseCases.fetchMyAllOrders()
        val requestsFlow = requestsUseCases.fetchMyAllRequests()

        combine(ordersFlow, requestsFlow) { orders, requests ->
            val orderItems = when (orders) {
                is ResultState.Success -> orders.result?.map { order ->
                    order.requestId.let { requestId ->
                        val request = requestsUseCases.getRequestById(requestId)
                        JobItem(
                            id = requestId,
                            trouble = request.trouble,
                            vehicle = request.vehicle.toModel(),
                            cost = request.cost,
                            status = order.status
                        )
                    }
                } ?: emptyList<JobItem>()

                is ResultState.Failure -> {
                    showToast.emit(orders.e ?: "Error fetching orders")
                    emptyList<JobItem>()
                }

                is ResultState.Loading -> emptyList<JobItem>()

                else -> emptyList<JobItem>()
            }

            val requestItems = when (requests) {
                is ResultState.Success -> requests.result?.map { request ->
                    JobItem(
                        id = request.id,
                        trouble = request.trouble,
                        vehicle = request.vehicle.toModel(),
                        cost = request.cost,
                        status = OrderStatus.Pending
                    )
                } ?: emptyList()

                is ResultState.Failure -> {
                    showToast.emit(requests.e ?: "Error fetching requests")
                    emptyList<JobItem>()
                }

                is ResultState.Loading -> emptyList()
            }

            orderItems + requestItems
        }.collect { combinedItems ->
            jobItemsStateFlow.value = combinedItems
        }
    }

    fun getVehicles() = viewModelScope.launch {
        vehiclesUseCases.fetchVehicles().collect { state ->
            when(state) {
                is ResultState.Loading -> {
                    vehiclesStateFlow.value = state.result?.map { it.toModel() } ?: emptyList()
                }
                is ResultState.Success -> {
                    vehiclesStateFlow.value = state.result?.map { it.toModel() } ?: emptyList()
                }
                is ResultState.Failure -> {
                    showToast.emit(state.e ?: "Unknown error")
                }
            }
        }
    }

}