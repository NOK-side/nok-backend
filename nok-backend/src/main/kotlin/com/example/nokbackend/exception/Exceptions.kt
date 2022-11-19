package com.example.nokbackend.exception

class LoginFailedException(message: String = "로그인 정보가 정확하지 않습니다.") : RuntimeException(message)

class UnidentifiedUserException(message: String? = null) : RuntimeException(message)
