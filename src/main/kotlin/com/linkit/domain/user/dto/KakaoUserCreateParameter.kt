package com.linkit.domain.user.dto

data class KakaoUserCreateParameter(
    val kakaoId: String,
    val lastAccessToken: String,
    val userId: Long
)