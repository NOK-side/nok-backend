package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication

data class ConfirmAuthenticationRequest(
    val id: Long,
    val target: String,
    val code: String
)

data class AuthenticationResponse(
    val id: Long,
    val target: String,
    val code: String
) {
    constructor(authentication: Authentication) : this(
        authentication.id,
        authentication.target,
        authentication.code
    )
}