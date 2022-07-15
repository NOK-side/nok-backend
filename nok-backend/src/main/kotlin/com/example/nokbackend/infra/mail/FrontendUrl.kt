package com.example.nokbackend.infra.mail

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "front")
@ConstructorBinding
data class FrontendUrl(
    val url: String
)