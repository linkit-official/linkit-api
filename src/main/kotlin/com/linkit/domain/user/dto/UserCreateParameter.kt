package com.linkit.domain.user.dto

import com.linkit.domain.user.model.LoginType

data class UserCreateParameter(
    val nickName: String,
    val email: String?,
    val loginType: LoginType
)