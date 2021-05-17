package com.linkit.webapi.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

object UserSessionUtils {
    fun getCurrentUserSession(): UserSession = SecurityContextHolder.getContext().authentication as UserSession

    fun setCurrentUserSession(authentication: Authentication) {
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
    }

    fun getCurrentUserId(): Long = getCurrentUserSession().userId

    fun getCurrentUsername(): String = getCurrentUserSession().username
}