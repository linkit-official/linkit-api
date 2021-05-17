package com.linkit.webapi.auth.dto

data class KakaoLoginRequest(
    val kakaoId: String,
    val accessToken: String
): LoginRequest()