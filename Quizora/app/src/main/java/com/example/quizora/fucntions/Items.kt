package com.example.quizora.fucntions

// ===== NORMAL CLASSES =====
data class User(
    val username : String = "Default",
    val id : Int?,
    val email: String?,
    val img : String?,
    val access : Int?,
)

// ===== DATABASE CLASSES =====
// REQUEST CLASSES
data class LoginReq(
    val email : String,
    val password : String,
)

data class SignupReq(
    val username: String?,
    val email: String?,
    val phoneNum : String?,
    val password: String,
)

// RESPONSE CLASSES
data class DefaultRes(
    val message : String?,
    val success :Boolean,
)

data class LoginRes(
    val user : User?,
    val message: String?,
    val success: Boolean,
)