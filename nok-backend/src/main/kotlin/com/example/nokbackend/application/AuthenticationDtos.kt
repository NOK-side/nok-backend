package com.example.nokbackend.application

data class ConfirmAuthenticationRequest(
    val id: Long,
    val target: String,
    val code: String
)

