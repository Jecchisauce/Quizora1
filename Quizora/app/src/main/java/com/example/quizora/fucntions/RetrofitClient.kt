package com.example.quizora.fucntions

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log full request & response
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        // Now safely access BASE_URL
        if (Global.BASE_URL.isNullOrEmpty()) {
            Global.BASE_URL = "192.168.18.6"
        }
        val gson = GsonBuilder()
            .setLenient() // Allows parsing non-strict JSON
            .create()
        val baseUrl = Global.BASE_URL ?: "192.168.18.6"
        Log.d("DEBUG", "Retrofit using BASE_URL: $baseUrl")

        Retrofit.Builder()
            .baseUrl("http://${baseUrl.trimEnd('/')}:80/quizora_api/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}