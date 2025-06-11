package com.zekewang.basemvvmandroid.utils

object AuthUtil {
    private var token: String? = null

    fun setToken(value: String) {
        token = value
    }

    fun getToken(): String? = token
}