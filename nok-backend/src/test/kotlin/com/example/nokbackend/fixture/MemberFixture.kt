package com.example.nokbackend.fixture

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.Password

const val email: String = "test@test.com"
const val memberId: String = "testMemberId"
const val name: String = "tester"
const val phoneNumber: String = "010-0000-0000"
val password: Password = Password("password")

fun aMember(): Member = Member(
    information = MemberInformation(memberId, email, name, phoneNumber, ""),
    password = password,
    role = Member.Role.USER,
    status = Member.Status.ACTIVE,
    id = 1L
)
