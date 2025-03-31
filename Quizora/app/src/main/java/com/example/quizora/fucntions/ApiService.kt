package com.example.quizora.fucntions

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")

    @POST("users/register")
    suspend fun signup(@Body registerReq: SignupReq): DefaultRes

    @POST("users/login")
    suspend fun login(@Body request: LoginReq): LoginRes

    @DELETE("users/{id}")
    suspend fun delUser(@Path("id") id: Int): DefaultRes

    // Example of a request body
//    @POST("comments")
//    suspend fun comment(@Body request: Comment): DefaultRes
//
//    // Endpoint to get items within a 1km radius
//    @GET("locations")
//    fun getLocations(
//        @Query("latitude") latitude: Double,
//        @Query("longitude") longitude: Double,
//        @Query("radius") radius: Double
//    ): Call<LocationResponse>
}