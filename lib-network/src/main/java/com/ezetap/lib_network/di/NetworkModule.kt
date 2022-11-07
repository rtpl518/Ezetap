package com.ezetap.lib_network.di

import android.content.Context
import com.ezetap.lib_network.BuildConfig
import com.ezetap.lib_network.config.ApiService
import com.ezetap.lib_network.config.InterceptorNetworkConnection
import com.ezetap.lib_network.constants.Constants
import com.ezetap.lib_network.request.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesNetworkInterceptor(@ApplicationContext context: Context): InterceptorNetworkConnection =
        InterceptorNetworkConnection(context)

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: InterceptorNetworkConnection): OkHttpClient =
        with(OkHttpClient.Builder()) {
            addInterceptor(interceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            connectTimeout(Constants.API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constants.API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            callTimeout(Constants.API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            build()
        }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRequestManager(apiService: ApiService): RequestManager = RequestManager(apiService)
}