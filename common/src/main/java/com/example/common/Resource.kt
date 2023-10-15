package com.example.common

sealed class Resource<out T>(val result : T? = null) {
    object Initial : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<T>(val data : T? = null) : Resource<T>(result = data)
    data class Failure<T>(val e : Exception? = null) : Resource<T>()
}
