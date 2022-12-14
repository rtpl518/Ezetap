package com.ezetap.di

import com.ezetap.data.MainRepository
import com.ezetap.lib_network.request.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMainRepository(requestManager: RequestManager) = MainRepository(requestManager)
}