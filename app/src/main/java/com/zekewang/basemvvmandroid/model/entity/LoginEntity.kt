package com.zekewang.basemvvmandroid.model.entity

data class LoginEntity(
    val code: Int = 0,
    val msg: String,
    val token: String
)