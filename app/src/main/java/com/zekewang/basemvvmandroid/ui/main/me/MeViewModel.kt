package com.zekewang.basemvvmandroid.ui.main.me

import androidx.lifecycle.viewModelScope
import com.zekewang.basemvvmandroid.base.BaseEventViewModel
import com.zekewang.basemvvmandroid.common.Event
import com.zekewang.basemvvmandroid.eventbus.FlowEventBus
import com.zekewang.basemvvmandroid.store.datamanage.UserInfoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(
    private val userInfoManager: UserInfoManager
) : BaseEventViewModel<MeIntent>() {
    val userInfoFlow: StateFlow<UserInfoManager.UserInfo> = userInfoManager.getUserInfoFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserInfoManager.UserInfo("", "", "", "", "")
        )

    // 退出登录
    fun logout() {
        FlowEventBus.post(Event.ForceLogout)
    }
}