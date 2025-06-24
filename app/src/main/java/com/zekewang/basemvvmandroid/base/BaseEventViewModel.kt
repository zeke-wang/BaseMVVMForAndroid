package com.zekewang.basemvvmandroid.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseEventViewModel<Intent> :BaseViewModel(){
    private val _pageEvent = MutableSharedFlow<Intent>(extraBufferCapacity = 1)
    val pageEvent: SharedFlow<Intent> get() = _pageEvent

    protected fun sendPageEvent(event: Intent) {
        _pageEvent.tryEmit(event)
    }
}