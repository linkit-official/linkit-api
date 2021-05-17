package com.linkit.webapi.auth.dto

data class AppleLoginRequest(
    val appleId: String,
    val accessToken: String
): LoginRequest()