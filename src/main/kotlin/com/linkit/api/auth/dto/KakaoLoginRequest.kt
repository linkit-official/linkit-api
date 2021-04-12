package com.linkit.api.auth.dto

data class KakaoLoginRequest(
    val kakaoId: String,
    val accessToken: String
): LoginRequest()