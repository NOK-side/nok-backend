package com.example.nokbackend.config

import com.example.nokbackend.security.HeaderArgumentResolver
import com.example.nokbackend.security.JwtInterceptor
import com.example.nokbackend.security.JwtSessionArgumentResolver
import com.example.nokbackend.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtArgsResolver: JwtSessionArgumentResolver,
    private val headerArgumentResolver: HeaderArgumentResolver
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtInterceptor())
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtArgsResolver)
        resolvers.add(headerArgumentResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "DELETE")
            .allowCredentials(true)
    }

    @Bean
    fun jwtInterceptor(): JwtInterceptor = JwtInterceptor(jwtTokenProvider)

}