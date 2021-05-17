package com.linkit.webapi.auth.service

import com.linkit.webapi.auth.dto.AppleLoginRequest
import com.linkit.webapi.auth.dto.AppleRegisterRequest
import com.linkit.core.utils.StringUtils
import com.linkit.domain.user.dto.AppleUserCreateParameter
import com.linkit.domain.user.dto.UserCreateParameter
import com.linkit.domain.user.model.LoginType
import com.linkit.domain.user.model.Role
import com.linkit.domain.user.model.User
import com.linkit.domain.user.service.AppleUserDomainService
import com.linkit.domain.user.service.UserDomainService
import org.springframework.stereotype.Service

@Service
class AppleAuthService(
    private val appleUserDomainService: AppleUserDomainService,
    private val userDomainService: UserDomainService
) : AbstractAuthService<AppleRegisterRequest, AppleLoginRequest>(userDomainService) {

    override fun validateRequest(registerRequest: AppleRegisterRequest) {
        registerRequest.email?.let {
            require(StringUtils.isEmailPattern(it)) { "Register email is invalid" }
        }
    }

    override fun isExistAccount(registerRequest: AppleRegisterRequest): Boolean {
        return appleUserDomainService.isExist(registerRequest.appleId)
    }

    override fun createAccount(registerRequest: AppleRegisterRequest): User {
        val user = userDomainService.create(
            UserCreateParameter(
                email = registerRequest.email,
                nickName = registerRequest.nickname ?: "NickName", // TODO 닉네임 제조기
                loginType = LoginType.KAKAO,
                roles = setOf(Role.ROLE_BASIC)
            )
        )
        appleUserDomainService.create(
            AppleUserCreateParameter(
                appleId = registerRequest.appleId,
                lastAccessToken = registerRequest.accessToken
            ),
            user = user
        )
        return user
    }

    override fun verifyAccount(loginRequest: AppleLoginRequest): User {
        return appleUserDomainService.get(loginRequest.appleId).user
    }

    override fun deleteAccount(userId: Long) {
        appleUserDomainService.delete(userId)
    }
}
