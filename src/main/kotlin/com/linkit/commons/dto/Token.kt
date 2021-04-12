package com.linkit.commons.dto

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val type: String
)