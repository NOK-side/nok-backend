package com.example.nokbackend.config

import com.example.nokbackend.infrastructure.mail.MailAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AopConfig {

    @Bean
    fun mailAspect(): MailAspect = MailAspect()
}