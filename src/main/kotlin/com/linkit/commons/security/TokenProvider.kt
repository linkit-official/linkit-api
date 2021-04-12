package com.linkit.commons.security

import com.linkit.domain.user.model.Role
import com.linkit.commons.dto.Token
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface TokenProvider {
    fun createToken(userId: Long, username: String, roles: Set<Role>): Token
    fun refreshToken(refreshToken: String): Token
    fun validateAccessToken(accessToken: String): Boolean
    fun removePrefix(token: String?): String
    fun getAuthentication(accessToken: String): Authentication
}

