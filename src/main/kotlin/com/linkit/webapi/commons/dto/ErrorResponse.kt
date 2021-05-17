package com.linkit.webapi.commons.dto

data class ErrorResponse(
    val error: ErrorInfo
) {
    data class ErrorInfo(
        val code: Int,
        val exception: String,
        val message: String,
        val path: String
    )
}