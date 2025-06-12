package com.zekewang.basemvvmandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zekewang.basemvvmandroid.network.ApiResult
import com.zekewang.basemvvmandroid.network.toApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {

    // ======= UI 状态 =======
    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow: StateFlow<Boolean> get() = _loadingFlow

    private val _toastFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val toastFlow: SharedFlow<String> get() = _toastFlow

    // ======= loading 控制 =======
    fun showLoading() {
        _loadingFlow.value = true
    }

    fun hideLoading() {
        _loadingFlow.value = false
    }

    suspend fun postToast(message: String) {
        _toastFlow.emit(message)
    }

    fun postToastSync(message: String) {
        _toastFlow.tryEmit(message)
    }

    // ======= launchFlow：统一封装 Flow 操作 =======
    protected fun <T> launchFlow(
        block: suspend () -> Flow<T>,
        onEach: suspend (T) -> Unit,
        onError: suspend (Throwable) -> Unit = { postToast("发生错误: ${it.message}") }
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                block().collect { onEach(it) }
            } catch (e: Throwable) {
                onError(e)
            } finally {
                hideLoading()
            }
        }
    }

    // ======= 通用挂载 suspend 操作 =======
    protected fun <T> launchSuspend(
        block: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit = { postToastSync("发生错误: ${it.message}") }
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                val result = block()
                onSuccess(result)
            } catch (e: Throwable) {
                onError(e)
            } finally {
                hideLoading()
            }
        }
    }

    // ======= 通用发送请求操作 =======
    protected fun <T> launchApi(
        call: suspend () -> Response<T>,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = { postToastSync(it) }
    ) {
        viewModelScope.launch {
            try {
                showLoading()
                when (val result = call().toApiResult()) {
                    is ApiResult.Success -> onSuccess(result.data)
                    is ApiResult.Error -> onError(result.message)
                }
            } catch (e: Exception) {
                onError("发生异常: ${e.message}")
            } finally {
                hideLoading()
            }
        }
    }
}
