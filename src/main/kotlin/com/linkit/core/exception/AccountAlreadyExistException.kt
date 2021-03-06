package com.linkit.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AccountAlreadyExistException: RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
}