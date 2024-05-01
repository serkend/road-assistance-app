package com.example.domain.vehicles.usecases

data class VehiclesUseCases(
    val fetchVehicles: FetchVehicles,
    val saveVehicle: SaveVehicle,
    val deleteVehicle: DeleteVehicle,
    val getVehicleById: GetVehicleById
)