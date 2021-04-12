package com.linkit.domain.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("User")
data class User(
    @Id
    val id: Long? = null,
    val uuid: String = UUID.randomUUID().toString().replace("-", ""),
    val nickName: String,
    var email: String?,
    val loginType: LoginType,
    val roles: Role = Role.ROLE_BASIC,
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedDateTime: LocalDateTime = createdDateTime
)