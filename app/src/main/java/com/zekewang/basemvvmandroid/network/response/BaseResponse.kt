package com.zekewang.basemvvmandroid.network.response

/**
 * @author wz
 * @create at 2023 11.12
 * @description:
 **/
data class BaseData<T>(
    val code: Int,
    val msg: String,
    val data: T?,
)

class BaseTokenData(
    var code: Int,
    var msg: String,
    var token: String?,
)

data class BaseRespNoData(
    val code: Int,
    val msg: String,
)

data class BasePageResponse<T>(
    val code: Int,
    val msg: String,
    val total: Int,
    val pages: Int,
    val rows: List<T> = emptyList(),
)