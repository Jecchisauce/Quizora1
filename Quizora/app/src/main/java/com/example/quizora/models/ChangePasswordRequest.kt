package com.example.quizora.models

data class ChangePasswordRequest(
    val email: String,
    val current_password: String,
    val new_password: String
)
