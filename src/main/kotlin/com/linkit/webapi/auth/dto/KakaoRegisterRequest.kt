package com.linkit.webapi.auth.dto

data class KakaoRegisterRequest(
    val kakaoId: String,
    val accessToken: String,
    override val nickname: String?,
    override val email: String?
): RegisterRequest(nickname, email)