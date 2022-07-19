package com.example.nokbackend.application

import javax.validation.constraints.Email

data class ConfirmAuthenticationCodeRequest(
    @field:Email
    val id: Long,
    val email: String,
    val code: String
)