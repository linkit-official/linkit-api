package com.linkit.commons.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UserNotFoundException: RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
}