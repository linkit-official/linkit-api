package com.linkit.commons.controller

import com.linkit.commons.dto.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck(): ApiResponse<String> = ApiResponse.of("health")
}