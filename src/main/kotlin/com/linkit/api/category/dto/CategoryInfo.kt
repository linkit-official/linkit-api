package com.linkit.api.category.dto

import com.linkit.domain.category.model.Category

data class CategoryInfo(
    val categoryId: String,
    val categoryName: String
) {
    companion object {
        fun from(category: Category): CategoryInfo {
            return CategoryInfo(
                categoryId = category.id,
                categoryName = category.name
            )
        }
    }
}