package com.linkit.api.auth.service

import com.linkit.api.auth.dto.LoginRequest
import com.linkit.api.auth.dto.RegisterRequest
import com.linkit.commons.exception.AccountAlreadyExistException
import com.linkit.domain.user.model.User
import com.linkit.domain.user.service.UserDomainService

abstract class AbstractAuthService<T : RegisterRequest, S : LoginRequest>(
    private val userDomainService: UserDomainService
) {
    fun register(registerRequest: T): User {
        if (isExistAccount(registerRequest)) {
            throw AccountAlreadyExistException("Account already exist")
        }
        validateRequest(registerRequest)
        return createAccount(registerRequest)
    }

    protected abstract fun validateRequest(registerRequest: T)
    protected abstract fun isExistAccount(registerRequest: T): Boolean
    protected abstract fun createAccount(registerRequest: T): User
    fun login(loginRequest: S): User {
        return verifyAccount(loginRequest)
    }

    protected abstract fun verifyAccount(loginRequest: S): User

    fun withDraw(userId: Long) {
        deleteAccount(userId)
        userDomainService.delete(userId)
    }

    protected abstract fun deleteAccount(userId: Long)
}
