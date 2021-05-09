package com.linkit.domain.category.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Category (
    @Id
    val id: String,
    val text: String
)