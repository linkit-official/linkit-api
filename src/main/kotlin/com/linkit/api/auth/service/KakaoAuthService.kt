package com.linkit.api.auth.service

import com.linkit.api.auth.dto.KakaoLoginRequest
import com.linkit.api.auth.dto.KakaoRegisterRequest
import com.linkit.commons.utils.StringUtils
import com.linkit.domain.user.dto.KakaoUserCreateParameter
import com.linkit.domain.user.dto.UserCreateParameter
import com.linkit.domain.user.model.LoginType
import com.linkit.domain.user.model.Role
import com.linkit.domain.user.model.User
import com.linkit.domain.user.service.KakaoUserDomainService
import com.linkit.domain.user.service.UserDomainService
import org.springframework.stereotype.Service

@Service
class KakaoAuthService(
    private val kakaoUserDomainService: KakaoUserDomainService,
    private val userDomainService: UserDomainService
) : AbstractAuthService<KakaoRegisterRequest, KakaoLoginRequest>(userDomainService) {

    override fun validateRequest(registerRequest: KakaoRegisterRequest) {
        registerRequest.email?.let {
            require(StringUtils.isEmailPattern(it)) { "Register email is invalid" }
        }
    }

    override fun isExistAccount(registerRequest: KakaoRegisterRequest): Boolean {
        return kakaoUserDomainService.isExist(registerRequest.kakaoId)
    }

    override fun createAccount(registerRequest: KakaoRegisterRequest): User {
        val user = userDomainService.create(
            UserCreateParameter(
                email = registerRequest.email,
                nickName = registerRequest.nickname ?: "NickName", // TODO 닉네임 제조기
                loginType = LoginType.KAKAO,
                roles = setOf(Role.ROLE_BASIC)
            )
        )
        kakaoUserDomainService.create(
            KakaoUserCreateParameter(
                kakaoId = registerRequest.kakaoId,
                lastAccessToken = registerRequest.accessToken
            ),
            user = user
        )
        return user
    }

    override fun verifyAccount(loginRequest: KakaoLoginRequest): User {
        return kakaoUserDomainService.get(loginRequest.kakaoId).user
    }

    override fun deleteAccount(userId: Long) {
        kakaoUserDomainService.delete(userId)
    }
}
