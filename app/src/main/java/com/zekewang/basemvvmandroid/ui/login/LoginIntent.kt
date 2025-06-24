package com.zekewang.basemvvmandroid.ui.login

sealed class LoginIntent {
    object LoginSuccess : LoginIntent()
}
