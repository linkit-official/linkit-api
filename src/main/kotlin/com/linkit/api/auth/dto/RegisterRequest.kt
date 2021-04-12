package com.linkit.api.auth.dto

abstract class RegisterRequest(
    open val nickname: String?,
    open val email: String?
)