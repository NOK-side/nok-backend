package com.example.nokbackend.application

import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDGenerator {
    fun generate(length: Int) = UUID.randomUUID().toString().take(length)
}