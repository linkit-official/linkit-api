package com.linkit.domain.user.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val uuid: String = UUID.randomUUID().toString().replace("-", ""),
    val nickName: String,
    var email: String?,
    val loginType: LoginType,
    @Convert(converter = RoleSetConverter::class)
    val roles: Set<Role>,
    @Column(updatable = false)
    @CreationTimestamp
    val createdDateTime: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    val updatedDateTime: LocalDateTime = createdDateTime
)