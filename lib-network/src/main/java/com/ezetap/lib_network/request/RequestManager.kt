package com.ezetap.lib_network.request

import com.ezetap.lib_network.config.ApiService
import com.ezetap.lib_network.data.*
import com.google.gson.Gson
import javax.inject.Inject

/**
* Class responsible to make the requests.
* */
class RequestManager @Inject constructor(private val apiService: ApiService) {

    /**
    * Request to get the UI data.
    * */
    suspend fun fetchCustomUI(): ResponseModel<Any> {
        with(NetworkRouter.invokeApiCall { apiService.fetchCustomUI() }) {
            return when (this) {
                is ResultType.Success -> ResponseModel(
                    Gson().fromJson(
                        data,
                        CustomUIResponse::class.java
                    ), null
                )
                is ResultType.Error -> ResponseModel(null, data)
            }
        }
    }

    /**
    * Request to get the image URL.
    * */
    suspend fun fetchImage(): ResponseModel<Any> {
        with(NetworkRouter.invokeApiCall { apiService.fetchImage() }) {
            return when (this) {
                is ResultType.Success -> ResponseModel(
                    Gson().fromJson(
                        data,
                        ImageResponse::class.java
                    ), null
                )
                is ResultType.Error -> ResponseModel(null, data)
            }
        }
    }
}