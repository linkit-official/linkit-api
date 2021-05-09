package com.linkit.domain.category.repository

import com.linkit.domain.category.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, String>