package com.example.core.common

sealed class ResultState<out T>(val result: T? = null) {
    data class Loading<T>(val data: T? = null) : ResultState<T>(result = data)
    data class Success<T>(val data: T? = null) : ResultState<T>(result = data)
    data class Failure<T>(val e: String? = null, val data: T? = null) : ResultState<T>(result = data)
    data object Initial : ResultState<Nothing>()
}

suspend fun <T> ResultState<T>.handleStateSuspended(
    onSuccess: suspend (result: T?) -> Unit,
    onFailure: suspend (e: String?) -> Unit,
    onLoading: suspend (result: T?) -> Unit = {},
    onInitial: () -> Unit = {},
) {
    when(this) {
        is ResultState.Success -> onSuccess(result)
        is ResultState.Loading -> onLoading(result)
        is ResultState.Failure -> onFailure(e)
        ResultState.Initial -> onInitial()
    }
}
