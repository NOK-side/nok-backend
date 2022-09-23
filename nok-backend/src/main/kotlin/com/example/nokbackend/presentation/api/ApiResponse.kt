package com.example.nokbackend.presentation.api

data class ApiResponse<T>(
    val message: String? = "",
    val body: T? = null
) {
    companion object {
        fun error(message: String?): ApiResponse<EmptyBody> = ApiResponse(message = message, body = EmptyBody)

        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)
    }
}

object EmptyBody