package com.svs.riakotlin.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object APIExecutor {
    private const val NETWORK_CALL_TIMEOUT = 60 * 3
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024

    fun getApiService(): ApiService {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().baseUrl(ApiService.APP_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}