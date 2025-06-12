package com.zekewang.basemvvmandroid.ui.login

import com.zekewang.basemvvmandroid.api.LoginApiService
import com.zekewang.basemvvmandroid.base.BaseViewModel
import com.zekewang.basemvvmandroid.model.LoginRequest
import com.zekewang.basemvvmandroid.utils.AuthManager
import com.zekewang.basemvvmandroid.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: LoginApiService,
    private val authManager: AuthManager,
    private val logger: Logger
) : BaseViewModel(){

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun login(username: String, password: String) {
        launchApi(
            call = { api.login(LoginRequest(username, password)) },
            onSuccess = { result ->
                _loginSuccess.value = true
                authManager.setToken(result.token)
                postToastSync("登录成功")
            }
        )
    }
}
