package com.linkit.api.auth.controller.v1

import com.linkit.api.auth.dto.KakaoLoginRequest
import com.linkit.api.auth.dto.KakaoRegisterRequest
import com.linkit.api.auth.service.AuthApiService
import com.linkit.commons.dto.ApiResponse
import com.linkit.commons.dto.Token
import com.linkit.commons.security.UserSessionUtils
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/auth")
class AuthController(
    private val authApiService: AuthApiService
) {
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/verify")
    fun verifyToken() = ApiResponse.of(UserSessionUtils.getCurrentUserId())

    @Transactional
    @PostMapping("/register/kakao")
    fun registerWithEmail(@RequestBody registerRequest: KakaoRegisterRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.registerKakaoUser(registerRequest))

    @Transactional
    @PostMapping("/login/kakao")
    fun loginWithEmail(@RequestBody loginRequest: KakaoLoginRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.loginKakaoUser(loginRequest))
}