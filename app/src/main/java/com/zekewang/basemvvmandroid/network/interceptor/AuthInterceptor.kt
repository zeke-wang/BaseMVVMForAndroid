package com.zekewang.basemvvmandroid.network.interceptor

import com.zekewang.basemvvmandroid.common.Event
import com.zekewang.basemvvmandroid.eventbus.FlowEventBus
import com.zekewang.basemvvmandroid.store.datamanage.AuthManager
import com.zekewang.basemvvmandroid.utils.Logger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import org.json.JSONObject
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

        // 发送经过修改的请求，获取响应
        val response = chain.proceed(modifiedRequest)
        val body = response.peekBody(Long.MAX_VALUE).string()//<---- Change
        if (response.isSuccessful) {
            if (body.contains("code")) {
                val jsonObject = JSONObject(body)
                //  val msg = jsonObject.optString("msg")
                when (jsonObject.optInt("code")) {
                    401 -> {
                        FlowEventBus.post(Event.ShowToast("认证过期，请重新登录！"))
                        // 退出登录
                        FlowEventBus.post(Event.ForceLogout)
                    }

                    403 -> {
                        MainScope().launch {
                            FlowEventBus.post(Event.ShowToast("没有权限，联系管理员！"))
                        }
                    }
                }
            }
        }
        return response
    }

}