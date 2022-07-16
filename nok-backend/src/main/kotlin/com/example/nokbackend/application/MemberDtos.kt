package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.Password

data class RegisterMemberRequest(
    val memberId: String,
    val email: String,
    val password: Password,
    val name: String,
    val role: Member.Role = Member.Role.NOTHING,
    val phoneNumber: String
) {
    fun toEntity() = Member(
        memberId = memberId,
        email = email,
        password = password,
        name = name,
        role = role,
        phoneNumber = phoneNumber,
        status = Member.Status.READY
    )
}