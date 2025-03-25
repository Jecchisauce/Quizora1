package com.example.quizora.api

import com.example.quizora.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register.php")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login.php")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("change_password.php")
    fun changePassword(@Body request: ChangePasswordRequest): Call<ChangePasswordResponse>
}
