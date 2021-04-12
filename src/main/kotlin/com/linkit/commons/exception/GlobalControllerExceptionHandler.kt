//package com.linkit.commons.exception
//
//import com.fasterxml.jackson.core.JsonProcessingException
//import com.linkit.commons.dto.ErrorResponse
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.method.HandlerMethod
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//
//@ControllerAdvice
//class GlobalControllerExceptionHandler {
//
//    @ExceptionHandler(Exception::class)
//    @ResponseBody
//    fun exceptionHandler(
//        e: Exception,
//        handlerMethod: HandlerMethod,
//        request: HttpServletRequest,
//        response: HttpServletResponse
//    ): ErrorResponse {
//        response.status = 405
//        return ErrorResponse(
//            error = ErrorResponse.ErrorInfo(
//                code = response.status,
//                message = e.javaClass.name,
//                description = e.message ?: "",
//                path = request.requestURI.toString()
//            )
//        )
//    }
//}