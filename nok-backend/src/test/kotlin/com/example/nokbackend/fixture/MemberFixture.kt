package com.example.nokbackend.fixture

import com.example.nokbackend.domain.member.LoginInformation
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.Password

const val email: String = "test@test.com"
const val memberId: String = "testMemberId"
const val name: String = "tester"
const val phoneNumber: String = "010-0000-0000"
const val userAgent: String = "IPHONE"
const val refreshToken: String = "vaild refresh token"
val password: Password = Password("password")

fun aMember(
    information: MemberInformation = MemberInformation(memberId, email, name, phoneNumber, ""),
    loginInformation: LoginInformation = LoginInformation(userAgent, refreshToken),
    password: Password = Password("password"),
    role: Member.Role = Member.Role.USER,
    status: Member.Status = Member.Status.ACTIVE,
    id: Long = 1L
): Member = Member(
    information = information,
    loginInformation = loginInformation,
    password = password,
    role = role,
    status = status,
    id = id
)
