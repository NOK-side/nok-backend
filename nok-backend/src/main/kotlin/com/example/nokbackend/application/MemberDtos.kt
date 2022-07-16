package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.Password
import javax.validation.constraints.Email

data class RegisterMemberRequest(
    val memberId: String,
    @field:Email
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