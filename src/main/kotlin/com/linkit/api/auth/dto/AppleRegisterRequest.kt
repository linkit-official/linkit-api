package com.linkit.api.auth.dto

data class AppleRegisterRequest(
    val appleId: String,
    val accessToken: String,
    override val nickname: String?,
    override val email: String?
): RegisterRequest(nickname, email)