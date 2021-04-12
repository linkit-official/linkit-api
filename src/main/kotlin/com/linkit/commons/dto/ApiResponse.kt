package com.linkit.commons.dto

import org.springframework.http.ResponseEntity


data class ApiResponse<T> (
    val result: T
) {
    companion object {
        fun <T> of(result: T) = ApiResponse(result)
    }
}
