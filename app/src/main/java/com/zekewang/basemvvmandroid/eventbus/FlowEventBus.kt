package com.zekewang.basemvvmandroid.eventbus

import com.zekewang.basemvvmandroid.common.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 全局的 Flow EventBus，支持定义的 sealed class Event
 */
object FlowEventBus {

    private val eventFlow = MutableSharedFlow<Event>(extraBufferCapacity = 64)

    /** 发送事件 */
    fun post(event: Event) {
        eventFlow.tryEmit(event)
    }

    /** 非 inline 版本，供内部使用 */
    fun <T : Event> subscribe(clazz: Class<T>): Flow<T> {
        return eventFlow
            .filter { clazz.isInstance(it) }
            .map {
                @Suppress("UNCHECKED_CAST")
                it as T
            }
    }

    /** inline + reified 封装版 */
    inline fun <reified T : Event> subscribe(): Flow<T> {
        return subscribe(T::class.java)
    }

    /** 直接在协程作用域内收集 */
    inline fun <reified T : Event> observe(
        scope: CoroutineScope,
        crossinline onEvent: (T) -> Unit)
    {
        scope.launch(Dispatchers.Main) {
            subscribe<T>().collect { onEvent(it) }
        }
    }
}

