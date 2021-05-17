package com.linkit.domain.user.service

import com.linkit.core.utils.KotlinUtils.toNullable
import com.linkit.domain.user.dto.UserCreateParameter
import com.linkit.core.exception.UserNotFoundException
import com.linkit.domain.user.model.User
import com.linkit.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDomainService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun create(parameter: UserCreateParameter): User {
        return save(
            User(
                email = parameter.email,
                nickName = parameter.nickName,
                loginType = parameter.loginType,
                roles = parameter.roles
            )
        )
    }

    @Transactional(readOnly = true)
    fun get(userId: Long): User {
        return load(userId)
    }

    @Transactional
    fun delete(userId: Long) {
        return remove(userId)
    }

    private fun save(user: User): User {
        return userRepository.save(user)
    }

    private fun load(userId: Long): User {
        return userRepository.findById(userId).toNullable() ?: throw UserNotFoundException()
    }

    private fun remove(userId: Long) {
        userRepository.deleteById(userId)
    }
}
