package com.linkit.api.auth.controller.v1

import com.linkit.api.auth.dto.AppleLoginRequest
import com.linkit.api.auth.dto.AppleRegisterRequest
import com.linkit.api.auth.dto.KakaoLoginRequest
import com.linkit.api.auth.dto.KakaoRegisterRequest
import com.linkit.api.auth.service.AuthApiService
import com.linkit.commons.dto.ApiResponse
import com.linkit.commons.dto.Token
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/auth")
class AuthController(
    private val authApiService: AuthApiService
) {
    @ApiOperation(
        value = "Validate Access Token API",
        notes = "accessToken 을 보내면 status를 반환해주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/verify")
    fun verifyToken() = ApiResponse.of(true)

    @ApiOperation(
        value = "Kakao 회원가입 API",
        notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인"
    )
    @Transactional
    @PostMapping("/register/kakao")
    fun registerWithKakao(@RequestBody registerRequest: KakaoRegisterRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.registerKakaoUser(registerRequest))

    @ApiOperation(
        value = "Kakao 로그인 API",
        notes = "Authorization Header 필요 없습니다."
    )
    @Transactional
    @PostMapping("/login/kakao")
    fun loginWithKakao(@RequestBody loginRequest: KakaoLoginRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.loginKakaoUser(loginRequest))

    @ApiOperation(
        value = "Apple 회원가입 API",
        notes = "Authorization Header 필요 없습니다."
    )
    @Transactional
    @PostMapping("/register/apple")
    fun registerWithApple(@RequestBody registerRequest: AppleRegisterRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.registerAppleUser(registerRequest))

    @ApiOperation(
        value = "Kakao 로그인 API",
        notes = "Authorization Header 필요 없습니다."
    )
    @Transactional
    @PostMapping("/login/apple")
    fun loginWithApple(@RequestBody loginRequest: AppleLoginRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.loginAppleUser(loginRequest))
}