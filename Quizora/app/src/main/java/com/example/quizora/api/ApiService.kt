package com.example.quizora.api

import com.example.quizora.models.LoginResponse
import com.example.quizora.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register.php")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}
