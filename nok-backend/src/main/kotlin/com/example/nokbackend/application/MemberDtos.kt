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
    val password: Password,
    val role: Member.Role = Member.Role.NOTHING,
    val status: Member.Status = Member.Status.ACTIVE,
    val authenticationId: Long,
    val authenticationCode: String
) {
    fun toEntity() = Member(
        memberId = memberId,
        email = email,
        name = name,
        phoneNumber = phoneNumber,
        password = password,
        role = role,
        status = status
    )
}

data class UpdateMemberRequest(
    val name: String,
    val phoneNumber: String
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

//TODO: ID 찾기가 필요할까요? 생각해보니깐 저희 이메일로 가입함 ㅠㅠ 그래서 이메일을 모르면 인증코드도 확인할 수 없음.. ㅠㅠ
data class FindMemberEmailRequest(
    val name: String,
    val phoneNumber: String
)

data class FindMemberEmailResponse(
    val email: String,
)

/*
    TODO: 비밀번호는 초기화하는게 좋을 거 같아요
    제가 생각한 로직 : 이메일 주소, 이름체크 해서 인증코드 보내기 -> 인증코드 확인 -> 메일로 초기화된 비밀번호 보내기
 */

data class FindMemberPasswordRequest(
    @field:Email
    val email: String,
    val name: String,
)

data class FindMemberPasswordResponse(
    val authId: Long,
)

data class InitMemberPasswordRequest(
    val authId: Long,
    val email: String,
    val code: String,
)