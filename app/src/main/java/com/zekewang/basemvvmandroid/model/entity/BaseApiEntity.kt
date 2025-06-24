package com.zekewang.basemvvmandroid.model.entity

data class BaseData<T>(
    val code: Int,
    val msg: String,
    val data: T?,
)

data class BaseOptionResult(
    val code: Int,
    val msg: String
)

data class BasePageResponse<T>(
    val code: Int,
    val msg: String,
    val total: Int,
    val pages: Int,
    val rows: List<T> = emptyList(),
)