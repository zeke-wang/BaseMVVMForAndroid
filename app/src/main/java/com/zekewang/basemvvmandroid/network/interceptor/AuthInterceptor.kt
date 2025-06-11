package com.zekewang.basemvvmandroid.network.interceptor

import com.zekewang.basemvvmandroid.utils.AuthUtil
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

/**
 * @author wz
 * @date   2023/6/30 11:32
 * @desc   登录拦截器
 */
class AuthInterceptor : Interceptor {
//    companion object {
//        private const val TAG: String = "AuthInterceptor"
//    }

    private val noTokenList = listOf("/login", "/dev-api/login")

    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()
        if (noTokenList.contains(originalRequest.url.encodedPath)) {
            // 不添加 Token，直接发送请求
            return chain.proceed(originalRequest)
        }
        val token = AuthUtil.getToken()

        // 如果 token 不存在，直接继续原始请求
        if (token.isNullOrEmpty()) {
            return chain.proceed(originalRequest)
        }

        // 添加 Authorization 头
        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(modifiedRequest)
    }

}