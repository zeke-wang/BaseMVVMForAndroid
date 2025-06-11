package com.zekewang.basemvvmandroid.ui.login

import androidx.lifecycle.viewModelScope
import com.zekewang.basemvvmandroid.api.LoginApiService
import com.zekewang.basemvvmandroid.base.BaseViewModel
import com.zekewang.basemvvmandroid.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: LoginApiService
) : BaseViewModel(){

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                showLoading()
                val response = api.login(LoginRequest(
                    username,
                    password
                )) // suspend 方法
                if (response.isSuccessful) {
                    _loginSuccess.value = true
                    postToast("登录成功")
                } else {
                    postToast("登录失败：${response.message()}")
                }
            } catch (e: Exception) {
                postToast("网络异常：${e.message}")
            } finally {
                hideLoading()
            }
        }
    }
}
