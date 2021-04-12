package com.linkit.domain.user.model

import javax.persistence.AttributeConverter

class RoleSetConverter : AttributeConverter<Set<Role>, String> {

    override fun convertToDatabaseColumn(attribute: Set<Role>): String {
        return attribute.joinToString(SPLIT_CHAR) { it.name }
    }

    override fun convertToEntityAttribute(string: String): Set<Role> {
        return string.split(",").map { Role.valueOf(it) }.toSet()
    }

    companion object {
        private const val SPLIT_CHAR = ","
    }
}