package com.linkit.api.auth.service

import com.linkit.api.auth.dto.LoginRequest
import com.linkit.api.auth.dto.RegisterRequest
import com.linkit.commons.exception.AccountAlreadyExistException
import com.linkit.domain.user.model.User

abstract class AbstractAuthService<T : RegisterRequest, S : LoginRequest> {
    fun register(registerRequest: T): User {
        if (isExistAccount(registerRequest)) {
            throw AccountAlreadyExistException()
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
}
