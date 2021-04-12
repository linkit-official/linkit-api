package com.linkit.commons.dto

data class ApiResponse<T> (
    val result: T
) {
    companion object {
        fun <T> of(result: T) = ApiResponse(result)
    }
}
