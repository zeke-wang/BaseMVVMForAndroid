package com.zekewang.basemvvmandroid.model

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val code: Int,
    val msg: String,
    val token: String
)