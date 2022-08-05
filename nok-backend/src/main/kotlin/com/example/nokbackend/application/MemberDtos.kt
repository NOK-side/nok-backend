package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.Password
import javax.validation.constraints.Email

data class RegisterMemberRequest(
    val memberId: String,
    @field:Email
    val email: String,
    val name: String,
    val phoneNumber: String,
    val profileImage: String,
    val password: Password,
    val role: Member.Role = Member.Role.NOTHING,
    val status: Member.Status = Member.Status.ACTIVE,
//    val authenticationId: Long,
//    val authenticationCode: String
) {
    fun toEntity() = Member(
        memberId = memberId,
        email = email,
        name = name,
        phoneNumber = phoneNumber,
        profileImg = profileImage,
        password = password,
        role = role,
        status = status
    )
}

data class UpdateMemberRequest(
    val name: String,
    val phoneNumber: String,
    val profileImage: String,
    val verificationPassword: Password
)

data class UpdatePasswordRequest(
    val oldPassword: Password,
    val newPassword: Password
)

data class WithdrawMemberRequest(
    @field:Email
    val email: String,
    val authenticationId: Long,
    val authenticationCode: String
)

data class LoginRequest(
    val memberId: String,
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

data class FindMemberIdRequest(
    val name: String,
    val phoneNumber: String
)

data class FindMemberIdResponse(
    val email: String,
    //FIXME: 임시 - 메일 보내기 비활성화 떄문에 ㅠㅠ
    val memberId: String,
)

data class FindMemberPasswordRequest(
    @field:Email
    val email: String,
    val name: String,
)

data class FindMemberPasswordResponse(
    val authId: Long,
    //FIXME: 임시 - 메일 보내기 비활성화 떄문에 ㅠㅠ
    val code: String,
)

data class InitMemberPasswordRequest(
    val authId: Long,
    val email: String,
    val code: String,
)

data class VerifyEmailRequest(
    @field:Email
    val email: String
)