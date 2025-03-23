package com.example.quizora.api

import com.example.quizora.models.LoginRequest
import com.example.quizora.models.LoginResponse
import com.example.quizora.models.RegisterRequest
import com.example.quizora.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register.php")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login.php")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
