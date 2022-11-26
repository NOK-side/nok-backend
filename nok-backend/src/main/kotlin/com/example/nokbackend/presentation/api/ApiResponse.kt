package com.example.nokbackend.presentation.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    var status: Int = 0,
    var message: String? = "",
    var data: T? = null
) {
    companion object {
        fun <T> success(body: T?, lazyMessage: () -> String = { "SUCCESS" }): ApiResponse<T> = ApiResponse(status = HttpStatus.OK.value(), data = body, message = lazyMessage())
    }
}

fun <T> responseEntity(block: ResponseEntityBuilder<T>.() -> Unit): ResponseEntity<T> {
    val responseEntityBuilder = ResponseEntityBuilder<T>()
    responseEntityBuilder.block()
    return responseEntityBuilder.build()
}

fun <T> apiResponse(block: ApiResponse<T>.() -> Unit): ApiResponse<T> {
    val apiResponse = ApiResponse<T>()
    apiResponse.block()
    return apiResponse
}

data class ResponseEntityBuilder<T>(
    var status: HttpStatus = HttpStatus.OK,
    var body: T? = null
) {
    fun build() = ResponseEntity.status(status).body(body)
}

object EmptyBody
