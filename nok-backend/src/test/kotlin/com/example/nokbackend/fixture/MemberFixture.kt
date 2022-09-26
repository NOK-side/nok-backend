package com.example.nokbackend.fixture

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.Password

const val email: String = "test@test.com"
const val memberId: String = "testMemberId"
const val name: String = "tester"
val phoneNumber: String = "010-0000-0000"
val password: Password = Password("password")

fun aMember(
    information: MemberInformation = MemberInformation(memberId, email, name, phoneNumber, ""),
    password: Password = Password("password"),
    role: Member.Role = Member.Role.USER,
    status: Member.Status = Member.Status.ACTIVE,
    id: Long = 1L
): Member = Member(
    information = information,
    password = password,
    role = role,
    status = status,
    id = id
)
