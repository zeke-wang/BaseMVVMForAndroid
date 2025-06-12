package com.zekewang.basemvvmandroid.network

import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
}

fun <T> Response<T>.toApiResult(): ApiResult<T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Error("响应体为空", code())
        }
    } else {
        val errorMsg = errorBody()?.string() ?: message()
        ApiResult.Error(errorMsg, code())
    }
}