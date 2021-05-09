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

    private fun loadAll() : List<Category> = categoryRepository.findAll()
}