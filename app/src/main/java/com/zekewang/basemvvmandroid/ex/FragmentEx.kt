package com.zekewang.basemvvmandroid.ex

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zekewang.basemvvmandroid.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 基于所有Flow进行收集
fun <T> Fragment.safeCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            flow.collect { collector(it) }
        }
    }
}

// 基于UiState类型的Flow收集
fun <T> Fragment.collectUiState(
    flow: StateFlow<UiState<T>>,
    onSuccess: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    safeCollect(flow) { state ->
        when (state) {
            is UiState.Loading -> onLoading?.invoke()
            is UiState.Success -> onSuccess(state.data)
            is UiState.Error -> onError?.invoke(state.exception)
            is UiState.Idle -> {}
        }
    }
}





