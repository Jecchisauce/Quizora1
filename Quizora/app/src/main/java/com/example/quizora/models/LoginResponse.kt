package com.example.quizora.models

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: Int? = null
)