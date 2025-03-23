package com.example.quizora.models

data class RegisterRequest(
    val nickname: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

