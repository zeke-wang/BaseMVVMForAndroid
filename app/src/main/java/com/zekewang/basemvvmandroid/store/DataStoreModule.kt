package com.zekewang.basemvvmandroid.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    @AuthDataStore
    fun provideAuthDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { File(context.filesDir, "auth_prefs.preferences_pb") }
        )
    }

    @Provides
    @Singleton
    @UserDataStore
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { File(context.filesDir, "user_prefs.preferences_pb") }
        )
    }

    @Provides
    @Singleton
    @AppConfigDataStore
    fun provideAppConfigDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { File(context.filesDir, "app_prefs.preferences_pb") }
        )
    }
}
