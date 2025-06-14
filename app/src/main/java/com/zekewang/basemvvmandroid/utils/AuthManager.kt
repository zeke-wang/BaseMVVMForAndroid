package com.zekewang.basemvvmandroid.utils

import com.zekewang.basemvvmandroid.store.LocalStorage
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val localStorage: LocalStorage
) {
    var token: String? = null
        private set

    suspend fun loginSuccess(token: String) {
        this.token = token
        localStorage.saveToken(token)
    }

    suspend fun logout() {
        token = null
        localStorage.clearToken()
    }

    suspend fun initAuthInfo() {
        this.token = localStorage.tokenFlow.first()
    }

    fun isLoggedIn(): Boolean = !token.isNullOrEmpty()

}
