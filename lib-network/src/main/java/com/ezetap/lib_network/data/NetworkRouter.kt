package com.ezetap.lib_network.data

import com.ezetap.lib_network.constants.ErrorCode
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Network class that works as a router.
 * Must interpret all api calls to grantee safety and exception handling.
 * */
object NetworkRouter {

    /**
     * Main and only one public fun in this class to invoke api calls safely granting error and exception handling.
     * All the mapping stuff to return only success or error model is done here.
     */
    suspend fun <T : Any> invokeApiCall(call: suspend () -> Response<T>): ResultType<T> {
        val response: Response<T>?
        return try {
            response = call.invoke()
            when {
                isValidResponse(response) -> ResultType.Success(response.body()!!)
                response.errorBody() != null -> ResultType.Error(
                    ErrorResponse(
                        ErrorCode.ERROR_RESPONSE,
                        "Getting error response, please try again!"
                    )
                )
                else -> ResultType.Error(
                    ErrorResponse(
                        ErrorCode.ERROR_UNKNOWN,
                        "Something went wrong!"
                    )
                )
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is IOException -> ResultType.Error(
                    ErrorResponse(
                        ErrorCode.ERROR_NO_NETWORK,
                        "Please check your internet connection!"
                    )
                )
                is HttpException -> ResultType.Error(
                    ErrorResponse(
                        ErrorCode.ERROR_SERVER,
                        "Getting http error!"
                    )
                )
                else -> ResultType.Error(
                    ErrorResponse(
                        ErrorCode.ERROR_UNKNOWN,
                        "Something went wrong!"
                    )
                )
            }
        }
    }

    /**
     * Check if call is successful.
     */
    private fun <T> isValidResponse(response: Response<T>?): Boolean {
        return if (response == null) false
        else (response.isSuccessful && response.body() != null)
    }
}
