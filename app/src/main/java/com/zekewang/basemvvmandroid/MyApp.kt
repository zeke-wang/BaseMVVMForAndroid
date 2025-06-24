package com.zekewang.basemvvmandroid

import android.app.Application
import com.zekewang.basemvvmandroid.store.datamanage.AppConfigManager
import com.zekewang.basemvvmandroid.store.datamanage.AuthManager
import com.zekewang.basemvvmandroid.store.datamanage.UserInfoManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject
    lateinit var authManager: AuthManager

//    @Inject
//    lateinit var userInfoManager: UserInfoManager

//    @Inject
//    lateinit var appConfigManager: AppConfigManager

    override fun onCreate() {
        super.onCreate()

        // 启动时加载 Token
        CoroutineScope(Dispatchers.IO).launch {
            authManager.loadToken()
        }
    }
}