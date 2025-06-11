package com.zekewang.basemvvmandroid.api

import com.zekewang.basemvvmandroid.model.LoginRequest
import com.zekewang.basemvvmandroid.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    /**
     * 登录
     */
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}