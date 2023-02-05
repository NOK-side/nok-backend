package com.example.nokbackend.application.util

import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDGenerator {
    fun generate(length: Int) = UUID.randomUUID().toString().take(length)
}