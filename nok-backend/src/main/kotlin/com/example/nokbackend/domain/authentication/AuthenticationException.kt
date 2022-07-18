package com.example.nokbackend.domain.authentication

class AuthenticationException : Exception {
    private val type: Type
    override val message: String

    constructor(type: Type) {
        this.type = type;
        this.message = when (type) {
            Type.EXPIRED -> "유효기간이 만료되었습니다."
            Type.DISCORD -> "인증코드가 불일치 합니다."
        }
    }

    enum class Type { EXPIRED, DISCORD, }
}