package com.zekewang.basemvvmandroid.store

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    // --------- 基础 put ---------
    fun putString(key: String, value: String?) {
        prefs.edit { putString(key, value) }
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    fun putInt(key: String, value: Int) {
        prefs.edit { putInt(key, value) }
    }

    // --------- 基础 get ---------
    fun getString(key: String, default: String? = null): String? {
        return prefs.getString(key, default)
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

    fun getInt(key: String, default: Int = 0): Int {
        return prefs.getInt(key, default)
    }

    // --------- 其他 ---------
    fun remove(key: String) {
        prefs.edit { remove(key) }
    }

    fun clear() {
        prefs.edit { clear() }
    }
}
