package com.linkit.api.auth.service

import com.linkit.api.auth.dto.KakaoLoginRequest
import com.linkit.api.auth.dto.KakaoRegisterRequest
import com.linkit.commons.dto.Token
import com.linkit.commons.security.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthApiService(
    private val tokenProvider: TokenProvider,
    private val kakaoAuthService: KakaoAuthService
) {
    @Transactional
    fun registerKakaoUser(registerRequest: KakaoRegisterRequest): Token {
        val user = kakaoAuthService.register(registerRequest)
        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
    }

    @Transactional
    fun loginKakaoUser(loginRequest: KakaoLoginRequest): Token {
        val user = kakaoAuthService.login(loginRequest)
        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
    }

    @Transactional
    fun refreshToken(refreshToken: String): Token {
        val token: String = tokenProvider.removePrefix(refreshToken)
        return tokenProvider.refreshToken(token)
    }
}

