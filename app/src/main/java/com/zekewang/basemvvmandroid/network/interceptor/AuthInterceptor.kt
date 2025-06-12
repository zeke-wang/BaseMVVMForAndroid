package com.zekewang.basemvvmandroid.network.interceptor

import android.util.Log
import com.zekewang.basemvvmandroid.utils.AuthManager
import com.zekewang.basemvvmandroid.utils.Logger
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

/**
 * @author wz
 * @date   2023/6/30 11:32
 * @desc   登录拦截器
 */
class AuthInterceptor @Inject constructor(
    private val authManager: AuthManager,
    private val logger: Logger
) : Interceptor {
    companion object {
        private const val TAG: String = "AuthInterceptor"
    }

    private val noTokenList = listOf("/login", "/dev-api/login")

    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()
        if (noTokenList.any { originalRequest.url.encodedPath.contains(it) }) {
            logger.d(TAG,"不需要token的请求")
            return chain.proceed(originalRequest)
        }
        val token = authManager.getToken()

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