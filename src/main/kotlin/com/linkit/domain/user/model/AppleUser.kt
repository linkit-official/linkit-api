package com.linkit.domain.user.model

import org.springframework.data.relational.core.mapping.Table

@Table("AppleUser")
data class AppleUser(
    val appleId: String,
    var lastAccessToken: String,
    val userId: Long,
)