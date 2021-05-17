package com.linkit.webapi.category.service

import com.linkit.webapi.category.dto.CategoryInfo
import com.linkit.webapi.category.dto.UserCategoryListRequest
import com.linkit.webapi.security.UserSessionUtils
import com.linkit.domain.category.dto.UserCategoryCreateParameter
import com.linkit.domain.category.service.CategoryDomainService
import com.linkit.domain.category.service.UserCategoryDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryDomainService: CategoryDomainService,
    private val userCategoryDomainService: UserCategoryDomainService
) {
    @Transactional
    fun getCategoryList(): List<CategoryInfo> = categoryDomainService.getAll().map { CategoryInfo.from(it) }

    @Transactional
    fun getUserCategoryList(): List<CategoryInfo> =
        categoryDomainService.getAll(userCategoryDomainService.getAll(UserSessionUtils.getCurrentUserId())
            .map { it.categoryId })
            .map { CategoryInfo.from(it) }


    @Transactional
    fun registerUserCategoryList(request: UserCategoryListRequest): List<CategoryInfo> {
        deleteUserCategoryList(UserSessionUtils.getCurrentUserId())
        return categoryDomainService.getAll(userCategoryDomainService.createAll(request.categoryIds.map {
            UserCategoryCreateParameter(userId = UserSessionUtils.getCurrentUserId(), categoryId = it)
        }).map { it.categoryId }).map { CategoryInfo.from(it) }
    }

    private fun deleteUserCategoryList(userId: Long) = userCategoryDomainService.deleteAll(userId)
}