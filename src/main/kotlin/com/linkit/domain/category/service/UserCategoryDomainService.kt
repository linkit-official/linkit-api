package com.linkit.domain.category.service

import com.linkit.domain.category.dto.UserCategoryCreateParameter
import com.linkit.domain.category.model.UserCategory
import com.linkit.domain.category.repository.UserCategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCategoryDomainService(
    private val userCategoryRepository: UserCategoryRepository
) {
    @Transactional
    fun createAll(parameters: List<UserCategoryCreateParameter>): List<UserCategory> {
        return saveAll(
            parameters.map {
                UserCategory(
                    userId = it.userId,
                    categoryId = it.categoryId
                )
            }
        )
    }

    @Transactional(readOnly = true)
    fun getAll(userId: Long): List<UserCategory> = loadAll(userId)

    @Transactional
    fun deleteAll(userId: Long) = removeAll(userId)

    private fun saveAll(userCategories: List<UserCategory>): List<UserCategory> =
        userCategories.also {
            userCategoryRepository.saveAll(it)
        }

    private fun loadAll(userId: Long): List<UserCategory> =
        userCategoryRepository.findAllByUserId(userId)

    private fun removeAll(userId: Long): Unit =
        userCategoryRepository.deleteAllByUserId(userId)
}