package com.linkit.webapi.commons.dto

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val type: String
)