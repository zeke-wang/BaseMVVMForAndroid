package com.zekewang.basemvvmandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zekewang.basemvvmandroid.common.Event
import com.zekewang.basemvvmandroid.common.UiState
import com.zekewang.basemvvmandroid.eventbus.FlowEventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    // ======= loading 控制 =======
    fun showLoading(message: String = "加载中") {
        FlowEventBus.post(Event.ShowLoading(message))
    }

    fun hideLoading() {
        FlowEventBus.post(Event.HideLoading)
    }

    // ======= toast 控制 =======
    fun tip(message: String) {
        FlowEventBus.post(Event.ShowToast(message))
    }

    // viewModel通用发送请求更新流状态
    protected fun <T> launchApi(
        state: MutableStateFlow<UiState<T>>,
        block: suspend () -> T
    ) {
        viewModelScope.launch {
            state.value = UiState.Loading
            showLoading()
            try {
                val result = block()
                state.value = UiState.Success(result)
            } catch (e: Exception) {
                showLoading(e.message ?: "未知错误")
                state.value = UiState.Error(e)
            } finally {
                hideLoading()
            }
        }
    }
}
