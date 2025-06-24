package com.zekewang.basemvvmandroid.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val avatar: String,
    val createTime: String,
    val email: String,
    val nickName: String,
    val phonenumber: String,
    val userName: String
)

data class UserInfoEntity(
    val code: Int,
    val msg: String,
    val permissions: List<String> = arrayListOf(),
    val roles: List<String>?,
    val user: UserEntity?
)

