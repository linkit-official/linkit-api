package com.linkit.webapi.commons.controller

import com.linkit.webapi.commons.dto.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HealthCheckController.BASE_URL)
class HealthCheckController {
    @GetMapping
    fun healthCheck(): ApiResponse<String> = ApiResponse.of("health")

    companion object {
        const val BASE_URL = "/health"
    }
}