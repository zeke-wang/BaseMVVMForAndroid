package com.zekewang.basemvvmandroid.api

import com.zekewang.basemvvmandroid.model.request.LoginRequest
import com.zekewang.basemvvmandroid.model.entity.LoginEntity
import com.zekewang.basemvvmandroid.model.entity.UserInfoEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    /**
     * 登录
     */
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginEntity

    /**
     * 获取用户信息
     */
    @GET("getInfo")
    suspend fun getInfo(): UserInfoEntity
}