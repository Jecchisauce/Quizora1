package com.example.quizora.api

import com.example.quizora.models.RegisterResponse
import com.example.quizora.models.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register.php")
    fun registerUser(@Body request: UserRequest): Call<RegisterResponse>

    @POST("login.php")
    fun loginUser(@Body request: UserRequest): Call<RegisterResponse>
}
