package com.linkit.domain.user.service

import com.linkit.commons.utils.KotlinUtils.toNullable
import com.linkit.domain.user.dto.KakaoUserCreateParameter
import com.linkit.commons.exception.UserNotFoundException
import com.linkit.domain.user.model.KakaoUser
import com.linkit.domain.user.repository.KakaoUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KakaoUserService(
    private val kakaoUserRepository: KakaoUserRepository
) {
    @Transactional
    fun create(parameter: KakaoUserCreateParameter): KakaoUser {
        return save(
            KakaoUser(
                kakaoId = parameter.kakaoId,
                lastAccessToken = parameter.lastAccessToken,
                userId = parameter.userId
            )
        )
    }

    @Transactional(readOnly = true)
    fun get(kakaoId: String): KakaoUser {
        return load(kakaoId)
    }

    @Transactional(readOnly = true)
    fun isExist(kakaoId: String): Boolean {
        return this.existsByKakaoId(kakaoId)
    }

    private fun existsByKakaoId(kakaoId: String): Boolean {
        return kakaoUserRepository.existsById(kakaoId)
    }

    private fun save(kakaoUser: KakaoUser): KakaoUser {
        return kakaoUserRepository.save(kakaoUser)
    }

    private fun load(kakaoId: String): KakaoUser {
        return kakaoUserRepository.findById(kakaoId).toNullable() ?: throw UserNotFoundException()
    }

    private fun loadByUserId(userId: Long): KakaoUser {
        return kakaoUserRepository.findByUserId(userId) ?: throw UserNotFoundException()
    }
}
