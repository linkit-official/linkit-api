package com.linkit.domain.user.service

import com.linkit.core.utils.KotlinUtils.toNullable
import com.linkit.domain.user.dto.AppleUserCreateParameter
import com.linkit.core.exception.UserNotFoundException
import com.linkit.domain.user.model.AppleUser
import com.linkit.domain.user.model.User
import com.linkit.domain.user.repository.AppleUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AppleUserDomainService(
    private val appleUserRepository: AppleUserRepository
) {
    @Transactional
    fun create(parameter: AppleUserCreateParameter, user: User): AppleUser {
        return save(
            AppleUser(
                appleId = parameter.appleId,
                lastAccessToken = parameter.lastAccessToken,
                user = user
            )
        )
    }

    @Transactional(readOnly = true)
    fun get(appleId: String): AppleUser {
        return load(appleId)
    }

    @Transactional(readOnly = true)
    fun isExist(appleId: String): Boolean {
        return this.existsByAppleId(appleId)
    }

    @Transactional
    fun delete(userId: Long) {
        this.deleteByUserId(userId)
    }

    private fun existsByAppleId(appleId: String): Boolean {
        return appleUserRepository.existsById(appleId)
    }

    private fun save(appleUser: AppleUser): AppleUser {
        return appleUserRepository.save(appleUser)
    }

    private fun load(appleId: String): AppleUser {
        return appleUserRepository.findById(appleId).toNullable() ?: throw UserNotFoundException()
    }

    private fun loadByUserId(userId: Long): AppleUser {
        return appleUserRepository.findByUserId(userId) ?: throw UserNotFoundException()
    }

    private fun deleteByUserId(userId: Long) {
        appleUserRepository.deleteByUserId(userId)
    }
}
