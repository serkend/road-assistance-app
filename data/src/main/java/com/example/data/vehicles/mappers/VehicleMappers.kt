package com.example.data.vehicles.mappers

import com.example.data.vehicles.dto.VehicleDto
import com.example.data.vehicles.entity.VehicleEntity
import com.example.domain.vehicles.model.Vehicle

fun VehicleDto.toEntity(): VehicleEntity = VehicleEntity(id, make, model, year)

fun Vehicle.toDto(id:String) = VehicleDto(id = id, make = make, model = model, year = year)

fun Vehicle.toDto() = VehicleDto(id = id ?: "", make = make, model = model, year = year)

fun VehicleDto.toDomain() = Vehicle(id = id, make = make, model = model, year = year)