package com.example.core.common.vehicles

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleModel(
    val id: String? = null,
    val make: String,
    val model: String,
    val year: Int
) : Parcelable
