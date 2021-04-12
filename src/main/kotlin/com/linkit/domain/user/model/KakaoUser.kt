package com.linkit.domain.user.model

import org.springframework.data.relational.core.mapping.Table

@Table("KakaoUser")
data class KakaoUser(
    val kakaoId: String,
    var lastAccessToken: String,
    val userId: Long,
)