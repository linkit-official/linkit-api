package com.linkit.api.category.service

import com.linkit.api.category.dto.CategoryInfo
import com.linkit.api.category.dto.UserCategoryListRequest
import com.linkit.commons.security.UserSessionUtils
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
        deleteUserCategoryList()
        return categoryDomainService.getAll(userCategoryDomainService.createAll(request.categoryIds.map {
            UserCategoryCreateParameter(userId = UserSessionUtils.getCurrentUserId(), categoryId = it)
        }).map { it.categoryId }).map { CategoryInfo.from(it) }
    }

    private fun deleteUserCategoryList() = userCategoryDomainService.deleteAll(UserSessionUtils.getCurrentUserId())
}