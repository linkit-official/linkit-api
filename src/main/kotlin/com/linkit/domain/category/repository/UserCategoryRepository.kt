package com.linkit.domain.category.repository

import com.linkit.domain.category.model.UserCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCategoryRepository : JpaRepository<UserCategory, Long> {
    fun findAllByUserId(userId: Long): List<UserCategory>
    fun deleteAllByUserId(userId: Long)
}