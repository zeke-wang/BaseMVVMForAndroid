package com.zekewang.basemvvmandroid.store

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppConfigDataStore
