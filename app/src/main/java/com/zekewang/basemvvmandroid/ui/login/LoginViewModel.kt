package com.zekewang.basemvvmandroid.ui.login

import androidx.lifecycle.viewModelScope
import com.zekewang.basemvvmandroid.api.LoginApiService
import com.zekewang.basemvvmandroid.base.BaseEventViewModel
import com.zekewang.basemvvmandroid.model.request.LoginRequest
import com.zekewang.basemvvmandroid.store.datamanage.AuthManager
import com.zekewang.basemvvmandroid.store.datamanage.UserInfoManager
import com.zekewang.basemvvmandroid.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: LoginApiService,
    private val authManager: AuthManager,
    private val userInfoManager: UserInfoManager,
    private val logger: Logger
) : BaseEventViewModel<LoginIntent>() {

    fun login(username: String, password: String) {
        viewModelScope.launch {
            showLoading("登录中")
            try {
                val loginResult = apiService.login(LoginRequest(username, password))
                if (loginResult.code == 200) {
                    authManager.saveToken(loginResult.token)
                    val userInfoRes = apiService.getUserInfo()
                    if (userInfoRes.code == 200) {
                        // 用户信息缓存
                        userInfoManager.saveUserInfo(userInfoRes)
//                        logger.d("test", "$userInfoRes")
                        tip("登录成功")
                        // 通知业务处理成功
                        sendPageEvent(LoginIntent.LoginSuccess)
                    } else {
                        tip("获取用户信息失败：${userInfoRes.msg}")
                    }
                } else {
                    tip("登录失败：${loginResult.msg}")
                }
            } catch (e: Exception) {
                tip("登录失败：${e.message}")
            } finally {
                hideLoading()
            }
        }
    }
}
