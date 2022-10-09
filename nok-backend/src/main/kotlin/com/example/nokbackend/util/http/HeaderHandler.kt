package com.example.nokbackend.util.http

class HeaderHandler {

    companion object {
        private const val BEARER = "Bearer"

        fun extractBearerToken(authorization: String): String {
            val (tokenType, token) = BearerHeader.splitToTokenFormat(authorization)

            if (tokenType != BEARER) {
                throw IllegalAccessException("Bearer 형식의 토큰이 아닙니다")
            }

            return token
        }
    }
}