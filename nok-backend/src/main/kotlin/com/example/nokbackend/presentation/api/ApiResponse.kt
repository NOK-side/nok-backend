package com.example.nokbackend.presentation.api

import org.springframework.http.HttpStatus

data class ApiResponse<T>(
    val status: Int,
    val message: String? = "",
    val body: T? = null
) {
    companion object {
        fun error(status: HttpStatus, message: String?): ApiResponse<EmptyBody> = ApiResponse(status = status.value(), message = message, body = EmptyBody)

        fun <T> success(body: T?, lazyMessage: () -> String = { "SUCCESS" }): ApiResponse<T> = ApiResponse(status = HttpStatus.OK.value(), body = body, message = lazyMessage())
    }
}

object EmptyBody