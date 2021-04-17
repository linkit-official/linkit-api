package com.linkit.api.auth.service

import com.linkit.api.auth.dto.AppleLoginRequest
import com.linkit.api.auth.dto.AppleRegisterRequest
import com.linkit.api.auth.dto.KakaoLoginRequest
import com.linkit.api.auth.dto.KakaoRegisterRequest
import com.linkit.commons.dto.Token
import com.linkit.commons.security.TokenProvider
import com.linkit.domain.user.model.LoginType
import com.linkit.domain.user.service.UserDomainService
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthApiService(
    private val tokenProvider: TokenProvider,
    private val kakaoAuthService: KakaoAuthService,
    private val appleAuthService: AppleAuthService,
    // TODO 리펙토링 가즈아
    private val userDomainService: UserDomainService
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
    fun registerAppleUser(registerRequest: AppleRegisterRequest): Token {
        val user = appleAuthService.register(registerRequest)
        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
    }

    @Transactional
    fun loginAppleUser(loginRequest: AppleLoginRequest): Token {
        val user = appleAuthService.login(loginRequest)
        return tokenProvider.createToken(user.id!!, user.nickName, user.roles)
    }

    @Transactional
    fun refreshToken(refreshToken: String): Token {
        val token: String = tokenProvider.removePrefix(refreshToken)
        return tokenProvider.refreshToken(token)
    }

    @Transactional
    fun withdrawUser(userId: Long) {
        val user = userDomainService.get(userId)
        when (user.loginType) {
            LoginType.KAKAO -> kakaoAuthService.withDraw(userId)
            LoginType.APPLE -> appleAuthService.withDraw(userId)
            LoginType.EMAIL -> logger.error { "Email User not supported" }
        }
    }

    companion object: KLogging()
}

