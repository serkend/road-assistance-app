package com.example.core.common

sealed class ResultState<out T>(val result: T? = null) {
    data class Loading<T>(val data: T? = null) : ResultState<T>(result = data)
    data class Success<T>(val data: T? = null) : ResultState<T>(result = data)
    data class Failure<T>(val e: String? = null, val data: T? = null) : ResultState<T>(result = data)
}
