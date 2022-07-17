package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.Password
import javax.validation.constraints.Email

data class RegisterMemberRequest(
    val memberId: String,
    @field:Email
    val email: String,
    val password: Password,
    val name: String,
    val role: Member.Role = Member.Role.NOTHING,
    val phoneNumber: String,
    val authenticationCode: String
) {
    fun toEntity() = Member(
        memberId = memberId,
        email = email,
        password = password,
        name = name,
        role = role,
        phoneNumber = phoneNumber
    )
}

data class LoginRequest(
    @field:Email
    val email: String,
    val password: Password
)

data class MemberInfoResponse(
    val memberInformation: MemberInformation,
    val role: Member.Role,
    val status: Member.Status
) {
    constructor(member: Member) : this(
        member.information,
        member.role,
        member.status
    )
}