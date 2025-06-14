package com.zekewang.basemvvmandroid.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class LocalStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val KEY_TOKEN = stringPreferencesKey("user_token")
        val KEY_USERNAME = stringPreferencesKey("user_info_json")
    }

    val tokenFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_TOKEN] ?: ""
        }

    val usernameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_USERNAME] ?: ""
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = username
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

}
