package com.linkit.api.auth.service

import com.linkit.api.auth.dto.AppleLoginRequest
import com.linkit.api.auth.dto.AppleRegisterRequest
import com.linkit.commons.utils.StringUtils
import com.linkit.domain.user.dto.AppleUserCreateParameter
import com.linkit.domain.user.dto.UserCreateParameter
import com.linkit.domain.user.model.LoginType
import com.linkit.domain.user.model.Role
import com.linkit.domain.user.model.User
import com.linkit.domain.user.service.AppleUserService
import com.linkit.domain.user.service.UserService
import org.springframework.stereotype.Service

@Service
class AppleAuthService(
    private val appleUserService: AppleUserService,
    private val userService: UserService
) : AbstractAuthService<AppleRegisterRequest, AppleLoginRequest>() {

    override fun validateRequest(registerRequest: AppleRegisterRequest) {
        registerRequest.email?.let {
            require(StringUtils.isEmailPattern(it)) { "Register email is invalid" }
        }
    }

    override fun isExistAccount(registerRequest: AppleRegisterRequest): Boolean {
        return appleUserService.isExist(registerRequest.appleId)
    }

    override fun createAccount(registerRequest: AppleRegisterRequest): User {
        val user = userService.create(
            UserCreateParameter(
                email = registerRequest.email,
                nickName = registerRequest.nickname ?: "NickName", // TODO 닉네임 제조기
                loginType = LoginType.KAKAO,
                roles = setOf(Role.ROLE_BASIC)
            )
        )
        appleUserService.create(
            AppleUserCreateParameter(
                appleId = registerRequest.appleId,
                lastAccessToken = registerRequest.accessToken
            ),
            user = user
        )
        return user
    }

    override fun verifyAccount(loginRequest: AppleLoginRequest): User {
        return appleUserService.get(loginRequest.appleId).user
    }
}
