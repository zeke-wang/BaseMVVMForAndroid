package com.zekewang.basemvvmandroid.common

sealed class Event {

    /** 显示普通提示 */
    data class ShowToast(val message: String) : Event()

    /** 显示加载框 */
    data class ShowLoading(val message: String) :Event()

    /** 影藏加载框 */
    object HideLoading: Event()

    /** 显示弹窗 (可用于全局弹窗控制) */
    data class ShowDialog(val title: String, val message: String) : Event()

    /** 强制全局退出登录 token过期 */
    object ForceLogout : Event()
}

