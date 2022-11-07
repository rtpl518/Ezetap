package com.ezetap.data

import com.ezetap.base.BaseRepository
import com.ezetap.lib_network.request.RequestManager
import javax.inject.Inject

class MainRepository @Inject constructor(private val requestManager: RequestManager) : BaseRepository() {

    suspend fun fetchCustomUI() = requestManager.fetchCustomUI()
    suspend fun fetchImage() = requestManager.fetchImage()
}