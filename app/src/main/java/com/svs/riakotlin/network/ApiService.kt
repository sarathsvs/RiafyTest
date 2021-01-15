package com.svs.riakotlin.network

import com.google.gson.JsonObject
import com.svs.riakotlin.models.DataMod
import com.svs.riakotlin.models.DataResponse
import com.svs.riakotlin.models.PostData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("kotlintest")
    suspend fun getData(): Response<List<DataMod>>

    @POST("kotlintest")
    suspend fun postData(
        @Body body: PostData
    ): Response<String>

    companion object{
        const val APP_BASE_URL: String = "http://fastingconsole.us-east-1.elasticbeanstalk.com/"
    }

}