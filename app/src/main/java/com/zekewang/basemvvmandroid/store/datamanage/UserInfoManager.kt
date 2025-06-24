package com.zekewang.basemvvmandroid.store.datamanage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zekewang.basemvvmandroid.store.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoManager @Inject constructor(
    @UserDataStore private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val AVATAR_KEY = stringPreferencesKey("avatar")
    }

    suspend fun saveUserInfo(username: String, avatar: String) {
        dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
            prefs[AVATAR_KEY] = avatar
        }
    }

    fun getUserInfoFlow(): Flow<Pair<String, String>> {
        return dataStore.data.map { prefs ->
            Pair(
                prefs[USERNAME_KEY] ?: "",
                prefs[AVATAR_KEY] ?: ""
            )
        }
    }
}