package com.example.nokbackend.exception

import org.springframework.http.HttpStatus

abstract class MemberException(
    override val message: String,
    open val status: HttpStatus
) : RuntimeException()

class UnidentifiedUserException(
    override val message: String = "인증되지 않은 사용자입니다.",
    override val status: HttpStatus = HttpStatus.FORBIDDEN
) : MemberException(message, status)

class LoginFailedException(
    override val message: String = "로그인 정보가 일치하지 않습니다.",
    override val status: HttpStatus = HttpStatus.BAD_REQUEST
) : MemberException(message, status)