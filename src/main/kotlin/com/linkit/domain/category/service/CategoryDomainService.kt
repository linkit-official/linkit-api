package com.linkit.domain.category.service

import com.linkit.domain.category.model.Category
import com.linkit.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryDomainService(
    private val categoryRepository: CategoryRepository
) {
    @Transactional(readOnly = true)
    fun getAll(): List<Category> = loadAll()

    @Transactional(readOnly = true)
    fun getAll(categoryIds: List<String>): List<Category> = loadAllByCategoryIds(categoryIds)

    private fun loadAll(): List<Category> = categoryRepository.findAll()

    private fun loadAllByCategoryIds(categoryIds: List<String>): List<Category> =
        categoryRepository.findAllById(categoryIds)
}