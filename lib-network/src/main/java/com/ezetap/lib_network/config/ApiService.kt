package com.ezetap.lib_network.config

import com.ezetap.lib_network.constants.Constants
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.ENDPOINT_FETCH_CUSTOM_UI)
    suspend fun fetchCustomUI(): Response<JsonObject>

    @GET(Constants.ENDPOINT_FETCH_IMAGE)
    suspend fun fetchImage(): Response<JsonObject>
}