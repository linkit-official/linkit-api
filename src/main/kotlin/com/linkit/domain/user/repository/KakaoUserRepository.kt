package com.linkit.domain.user.repository

import com.linkit.domain.user.model.KakaoUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KakaoUserRepository : JpaRepository<KakaoUser, String> {
    fun findByUserId(userId: Long): KakaoUser?
    fun deleteByUserId(userId: Long)
}