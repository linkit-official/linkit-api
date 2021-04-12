package com.linkit.commons.controller

import com.linkit.commons.dto.ErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class GlobalErrorController @Autowired constructor(
    errorAttributes: ErrorAttributes
) : AbstractErrorController(errorAttributes) {

    companion object {
        const val ERROR_PATH = "\${error.path:/error}"
        val errorAttributeOptions: ErrorAttributeOptions = ErrorAttributeOptions.of(
            ErrorAttributeOptions.Include.EXCEPTION,
            ErrorAttributeOptions.Include.BINDING_ERRORS,
            ErrorAttributeOptions.Include.MESSAGE
        )
    }

    @RequestMapping(ERROR_PATH)
    fun error(request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val status = getStatus(request)
        if (status == HttpStatus.NO_CONTENT) {
            return ResponseEntity<ErrorResponse>(status)
        }
        val errorAttributes: Map<String, Any> = getErrorAttributes(request, errorAttributeOptions)

        val errorResponse = ErrorResponse(
            error = ErrorResponse.ErrorInfo(
                code = status.value(),
                exception = (errorAttributes["exception"] as String).substringAfterLast("."),
                message = errorAttributes["message"] as String,
                path = errorAttributes["path"] as String
            )
        )
        return ResponseEntity<ErrorResponse>(errorResponse, status)
    }

    override fun getErrorPath() = ERROR_PATH
}