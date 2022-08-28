package com.example.nokbackend.fixture

import com.example.nokbackend.domain.authentication.Authentication
import java.time.LocalDateTime

fun aRegisterAuthentication(
    expirationDate: LocalDateTime = LocalDateTime.now().plusMinutes(10)
): Authentication = Authentication(
    id = 1L,
    target = aMember().email,
    code = aUuid(),
    createDate = LocalDateTime.now(),
    expireDate = expirationDate,
    type = Authentication.Type.REGISTER,
    status = Authentication.Status.READY
)

fun aWithdrawAuthentication(
    expirationDate: LocalDateTime = LocalDateTime.now().plusMinutes(10)
): Authentication = Authentication(
    id = 1L,
    target = aMember().email,
    code = aUuid(),
    createDate = LocalDateTime.now(),
    expireDate = expirationDate,
    type = Authentication.Type.WITHDRAW,
    status = Authentication.Status.READY
)
