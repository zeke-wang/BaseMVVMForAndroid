package com.zekewang.basemvvmandroid.store.datamanage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zekewang.basemvvmandroid.model.entity.UserInfoEntity
import com.zekewang.basemvvmandroid.store.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoManager @Inject constructor(
    @UserDataStore private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val NICKNAME_KEY = stringPreferencesKey("nickname")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("phoneNumber")
        private val AVATAR_KEY = stringPreferencesKey("avatar")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val PERMISSION_KEY = stringPreferencesKey("role")
    }

    suspend fun saveUserInfo(
        userInfoEntity: UserInfoEntity
    ) {
        dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = userInfoEntity.user?.userName ?: ""
            prefs[NICKNAME_KEY] = userInfoEntity.user?.nickName ?: ""
            prefs[AVATAR_KEY] = userInfoEntity.user?.avatar ?: ""
            prefs[PHONE_NUMBER_KEY] = userInfoEntity.user?.phonenumber ?: ""
            prefs[EMAIL_KEY] = userInfoEntity.user?.email ?: ""
            prefs[ROLE_KEY] = Json.encodeToString(userInfoEntity.roles)
            prefs[PERMISSION_KEY] = Json.encodeToString(userInfoEntity.permissions)
        }
    }

    // 读取完整用户信息
    fun getUserInfoFlow(): Flow<UserInfo> {
        return dataStore.data.map { prefs ->
            UserInfo(
                username = prefs[USERNAME_KEY] ?: "",
                nickname = prefs[NICKNAME_KEY] ?: "",
                phoneNumber = prefs[PHONE_NUMBER_KEY] ?: "",
                avatar = prefs[AVATAR_KEY] ?: "",
                email = prefs[EMAIL_KEY] ?: ""
            )
        }
    }

    // 数据类，外部调用方便又安全
    data class UserInfo(
        val username: String,
        val nickname: String,
        val phoneNumber: String,
        val avatar: String,
        val email: String
    )
}

