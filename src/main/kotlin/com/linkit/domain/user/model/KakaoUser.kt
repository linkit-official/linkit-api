package com.linkit.domain.user.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
class KakaoUser(
    @Id
    val kakaoId: String,
    var lastAccessToken: String,
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "userId", updatable = false)
    val user: User
)