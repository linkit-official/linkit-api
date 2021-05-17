package com.linkit.webapi.security

import com.linkit.webapi.security.UserSessionUtils.setCurrentUserSession
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter(
    requestMatcher: RequestMatcher
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = resolveToken(request)
        return UsernamePasswordAuthenticationToken(token, token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        setCurrentUserSession(authResult)
        chain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION)
    }
}
