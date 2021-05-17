package com.linkit.webapi.commons.controller

import com.linkit.webapi.commons.dto.ErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(GlobalErrorController.ERROR_PATH)
class GlobalErrorController @Autowired constructor(
    errorAttributes: ErrorAttributes
) : AbstractErrorController(errorAttributes) {

    @RequestMapping
    fun error(request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        val status = getStatus(request)
        if (status == HttpStatus.NO_CONTENT) {
            return ResponseEntity<ErrorResponse>(status)
        }
        val errorAttributes: Map<String, Any> = getErrorAttributes(request, errorAttributeOptions)

        val errorResponse = ErrorResponse(
            error = ErrorResponse.ErrorInfo(
                code = status.value(),
                exception = ((errorAttributes["exception"] ?: DEFAULT_EXCEPTION) as String).substringAfterLast("."),
                message = errorAttributes["message"] as String,
                path = errorAttributes["path"] as String
            )
        )
        return ResponseEntity<ErrorResponse>(errorResponse, status)
    }

    override fun getErrorPath() = ERROR_PATH

    companion object {
        const val ERROR_PATH = "/error"
        const val DEFAULT_EXCEPTION = "java.lang.Exception"
        val errorAttributeOptions: ErrorAttributeOptions = ErrorAttributeOptions.of(
            ErrorAttributeOptions.Include.EXCEPTION,
            ErrorAttributeOptions.Include.BINDING_ERRORS,
            ErrorAttributeOptions.Include.MESSAGE
        )
    }
}