package com.linkit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LinkitApplication

fun main(args: Array<String>) {
    runApplication<LinkitApplication>(*args)
}
