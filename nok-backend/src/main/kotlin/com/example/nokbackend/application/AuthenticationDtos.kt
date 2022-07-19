package com.example.nokbackend.application

import javax.validation.constraints.Email

data class ConfirmAuthenticationRequest(
    @field:Email
    val id: Long,
    val target: String,
    val code: String
)

