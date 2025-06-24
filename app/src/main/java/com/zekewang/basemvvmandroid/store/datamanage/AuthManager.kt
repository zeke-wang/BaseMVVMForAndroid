package com.zekewang.basemvvmandroid.store.datamanage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zekewang.basemvvmandroid.store.AuthDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    @AuthDataStore private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    private var token: String? = null

    // 启动时初始化
    suspend fun loadToken() {
        token = dataStore.data
            .map { preferences -> preferences[TOKEN_KEY] ?: "" }
            .firstOrNull()
    }

    // 保存 Token（同步更新内存）
    suspend fun saveToken(value: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = value
        }
        token = value
    }

    fun getToken(): String? = token

}
