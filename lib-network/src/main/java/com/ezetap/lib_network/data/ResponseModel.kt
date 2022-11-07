package com.ezetap.lib_network.data

data class ResponseModel<T>(
    val result: T?,
    val errorResponse: ErrorResponse?
)
