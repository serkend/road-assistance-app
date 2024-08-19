package com.example.core.uikit.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatTimestamp(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}