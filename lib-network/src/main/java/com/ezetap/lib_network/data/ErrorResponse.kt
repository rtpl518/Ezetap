package com.ezetap.lib_network.data

import com.ezetap.lib_network.constants.ErrorCode

data class ErrorResponse(val errorCode: ErrorCode, val error: String?)