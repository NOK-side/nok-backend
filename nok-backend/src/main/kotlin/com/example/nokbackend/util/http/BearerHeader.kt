package com.example.nokbackend.util.http

class BearerHeader {
    companion object {
        private const val DELIMITER = " "
        private const val BEARER = "Bearer "

        fun splitToTokenFormat(authorization: String): Pair<String, String> {
            return try {
                val tokenFormat = authorization.split(DELIMITER)
                tokenFormat[0] to tokenFormat[1]
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalAccessException()
            }
        }

        fun of(token: String) = BEARER + token
    }
}