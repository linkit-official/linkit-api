package com.linkit.commons.security

import com.linkit.domain.user.model.Role
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserSession(
    val userId: Long,
    val username: String,
    val roles: Set<Role>
) : AbstractAuthenticationToken(roles.map { SimpleGrantedAuthority(it.name) }) {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return super.getAuthorities()
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }
}
