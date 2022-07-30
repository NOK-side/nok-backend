package com.example.nokbackend.presentation.api

import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val message: String? = "",
    val body: T? = null
) {
    companion object {
        fun error(message: String?): ApiResponse<Unit> = ApiResponse(message = message)

        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)

        //TODO: 이거 별론가요?? ㅠㅠ -> 모든 성공 응답을 200으로만 내려준다면 상관없을거같은데 만약 데이터 생성시 201같은 다른 응답코드를 줘야한다면 적합하지 않을거같아요
        fun <T> ok(body : T?): ResponseEntity<Any> = ResponseEntity.ok().body(success(body = body))
    }
}
