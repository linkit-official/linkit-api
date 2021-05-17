package com.linkit.webapi.auth.controller.v1

import com.linkit.webapi.auth.dto.AppleLoginRequest
import com.linkit.webapi.auth.dto.AppleRegisterRequest
import com.linkit.webapi.auth.dto.KakaoLoginRequest
import com.linkit.webapi.auth.dto.KakaoRegisterRequest
import com.linkit.webapi.auth.service.AuthApiService
import com.linkit.webapi.commons.dto.ApiResponse
import com.linkit.webapi.commons.dto.Token
import com.linkit.webapi.security.UserSessionUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Api(tags = [AuthController.API_TAG])
@RestController
@RequestMapping(AuthController.BASE_URL)
class AuthController(
    private val authApiService: AuthApiService
) {
    @ApiOperation(
        nickname = "verifyToken",
        value = "Validate Access Token API",
        notes = "accessToken 을 보내면 status를 반환해주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/verify")
    fun verifyToken() = ApiResponse.of(true)

    @ApiOperation(
        nickname = "withdrawUser",
        value = "탈퇴 API",
        notes = "accessToken 을 보내면 탈퇴시키는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @DeleteMapping
    fun withdrawUser() = ApiResponse.of(authApiService.withdrawUser(UserSessionUtils.getCurrentUserId()))
    
    @ApiOperation(
        nickname = "registerWithKakao",
        value = "Kakao 회원가입 API",
        notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인"
    )
    @Transactional
    @PostMapping("/register/kakao")
    fun registerWithKakao(@RequestBody registerRequest: KakaoRegisterRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.registerKakaoUser(registerRequest))

    @ApiOperation(
        nickname = "loginWithKakao",
        value = "Kakao 로그인 API",
        notes = "Authorization Header 필요 없습니다."
    )
    @Transactional
    @PostMapping("/login/kakao")
    fun loginWithKakao(@RequestBody loginRequest: KakaoLoginRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.loginKakaoUser(loginRequest))

    @ApiOperation(
        nickname = "registerWithApple",
        value = "Apple 회원가입 API",
        notes = "Authorization Header 필요 없습니다."
    )
    @Transactional
    @PostMapping("/register/apple")
    fun registerWithApple(@RequestBody registerRequest: AppleRegisterRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.registerAppleUser(registerRequest))

    @ApiOperation(
        nickname = "loginWithApple",
        value = "Apple 로그인 API",
        notes = "Authorization Header 필요 없습니다."
    )
    @Transactional
    @PostMapping("/login/apple")
    fun loginWithApple(@RequestBody loginRequest: AppleLoginRequest): ApiResponse<Token> =
        ApiResponse.of(authApiService.loginAppleUser(loginRequest))

    companion object {
        const val API_TAG = "Auth"
        const val BASE_URL = "/v1/auth"
    }
}