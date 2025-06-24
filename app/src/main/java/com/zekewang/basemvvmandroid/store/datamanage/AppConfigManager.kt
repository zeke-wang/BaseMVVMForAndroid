package com.zekewang.basemvvmandroid.store.datamanage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.zekewang.basemvvmandroid.store.AppConfigDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigManager @Inject constructor(
    @AppConfigDataStore private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }

    fun isDarkMode(): Flow<Boolean> {
        return dataStore.data.map { prefs -> prefs[DARK_MODE_KEY] ?: false }
    }
}
