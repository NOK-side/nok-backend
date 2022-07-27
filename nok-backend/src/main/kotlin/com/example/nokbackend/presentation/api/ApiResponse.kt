package com.example.nokbackend.presentation.api

import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val message: String? = "",
    val body: T? = null
) {
    companion object {
        fun error(message: String?): ApiResponse<Unit> = ApiResponse(message = message)

        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)

        //TODO: 이거 별론가요?? ㅠㅠ
        fun <T> ok(body : T?): ResponseEntity<Any> = ResponseEntity.ok().body(success(body = body))
    }
}
