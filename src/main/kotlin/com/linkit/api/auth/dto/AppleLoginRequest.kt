package com.linkit.api.auth.dto

data class AppleLoginRequest(
    val appleId: String,
    val accessToken: String
): LoginRequest()