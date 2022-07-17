package com.example.nokbackend.application

import javax.validation.constraints.Email

data class ConfirmAuthenticationCodeRequest(
    @field:Email
    val email: String,
    val code: String
)