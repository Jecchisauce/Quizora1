package com.example.quizora.api

data class UserRequest(
    val name: String?,  // For signup
    val username: String,
    val password: String
)
