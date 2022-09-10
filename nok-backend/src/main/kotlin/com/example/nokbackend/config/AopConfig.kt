package com.example.nokbackend.config

import com.example.nokbackend.infrastructure.mail.MailAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AopConfig {

    @Bean
    fun mailAspect(): MailAspect = MailAspect()
}

//squ_dc6d563d63a468a6588a0e15c0684e5ba7fb07c6