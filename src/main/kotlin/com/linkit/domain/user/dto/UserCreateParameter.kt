package com.linkit.domain.user.dto

import com.linkit.domain.user.model.LoginType
import com.linkit.domain.user.model.Role

data class UserCreateParameter(
    val nickName: String,
    val email: String?,
    val loginType: LoginType,
    val roles: Set<Role>
)