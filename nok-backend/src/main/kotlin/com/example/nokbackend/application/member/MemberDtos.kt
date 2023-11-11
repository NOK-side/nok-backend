package com.example.nokbackend.application.member

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.LoginInformation
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
    val authenticationId: Long,
    val authenticationCode: String,
    val fcmCode: String,
) {
    fun toEntity(loginInformation: LoginInformation = LoginInformation("", "", "")) = Member(
        MemberInformation(
            memberId = memberId,
            email = email,
            name = name,
            phoneNumber = phoneNumber,
            profileImage = profileImage
        ),
        loginInformation,
        password = password,
        role = role,
        status = status
    )
}

data class UpdateMemberRequest(
    val name: String?,
    val phoneNumber: String?,
    val profileImage: String?
)

data class UpdateMemberResponse(
    val message: String,
)

data class UpdatePasswordRequest(
    val oldPassword: Password,
    val newPassword: Password,
)

data class UpdatePasswordResponse(
    val message: String,
)

data class WithdrawMemberRequest(
    val password: Password,
)

data class WithdrawMemberResponse(
    val message: String,
)

data class LoginRequest(
    val memberId: String,
    val password: Password,
    val fcmCode: String,
)

data class MemberInfoResponse(
    val memberInformation: MemberInformation,
    val role: Member.Role,
    val status: Member.Status,
) {
    constructor(member: Member) : this(
        member.information,
        member.role,
        member.status
    )
}

data class FindMemberIdRequest(
//    val name: String,
    val phoneNumber: String,
)

data class FindMemberIdResponse(
//    val email: String,
    //FIXME: 임시 - 메일 보내기 비활성화 떄문에 ㅠㅠ
    val memberId: String,
)

data class FindMemberPasswordRequest(
    val memberId: String,
)

data class FindMemberPasswordResponse(
    val email: String,
)

data class VerifyEmailRequest(
    @field:Email
    val email: String,
    val type: Authentication.Type,
)

data class CheckMemberIdDuplicationRequest(
    val memberId: String,
)

data class LoginResponse(
    val memberId: String,
    val email: String,
    val name: String,
    var profileImage: String,
    var role: Member.Role,
    val tokenResponse: TokenResponse,
) {
    constructor(member: Member, tokenResponse: TokenResponse) : this(
        member.memberId,
        member.email,
        member.name,
        member.profileImage,
        member.role,
        tokenResponse
    )
}

data class RefreshTokenRequest(
    val refreshToken: String,
    val fcmCode: String
)

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)