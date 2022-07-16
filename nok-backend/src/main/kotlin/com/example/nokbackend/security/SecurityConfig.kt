package com.example.nokbackend.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.DefaultSecurityFilterChain

@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun configure(http: HttpSecurity): DefaultSecurityFilterChain =
        run {
            http {
                csrf { disable() }
            }
            http.build()
        }
}