package com.ezetap.lib_network.data

sealed class ResultType<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultType<T>()
    data class Error(val data: ErrorResponse) : ResultType<Nothing>()
}
