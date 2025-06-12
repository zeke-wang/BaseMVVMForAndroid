package com.zekewang.basemvvmandroid.utils

import com.zekewang.basemvvmandroid.store.AppPreferences
import com.zekewang.basemvvmandroid.store.PrefKeys
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val prefs: AppPreferences
) {
    fun setToken(token: String) {
        prefs.putString(PrefKeys.TOKEN, token)
    }

    fun getToken(): String? {
        return prefs.getString(PrefKeys.TOKEN)
    }

    fun clearToken() {
        prefs.remove(PrefKeys.TOKEN)
    }
}
