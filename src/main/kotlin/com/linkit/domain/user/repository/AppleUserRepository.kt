package com.linkit.domain.user.repository

import com.linkit.domain.user.model.AppleUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppleUserRepository : JpaRepository<AppleUser, String> {
    fun findByUserId(userId: Long): AppleUser?
}