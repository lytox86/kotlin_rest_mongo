package com.tngtech.kotlin.workshop._07.rest.controller

import java.util.concurrent.atomic.AtomicLong
import com.tngtech.kotlin.workshop._07.rest.model.Greeting

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class GreetingController {
    // test it with GET http://localhost:8080/api/greeting?name=World
    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")
}

