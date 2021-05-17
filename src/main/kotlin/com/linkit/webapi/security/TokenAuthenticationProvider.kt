package com.linkit.webapi.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class TokenAuthenticationProvider(private val tokenProvider: TokenProvider) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val token = tokenProvider.removePrefix(authentication.principal?.toString())
        return tokenProvider.getAuthentication(token)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}
