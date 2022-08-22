package com.example.nokbackend.domain.member

import com.example.nokbackend.application.RegisterMemberRequest
import com.example.nokbackend.application.UpdateMemberRequest

class MemberFixture {

    companion object {
        val memberRequest: RegisterMemberRequest
            get() = RegisterMemberRequest(
                memberId = "rkdals213",
                email = "rkdals213@naver.com",
                name = "kang",
                phoneNumber = "000-0000-0000",
                profileImage = "",
                password = Password("1q2w3e4r"),
                role = Member.Role.USER,
                authenticationId = 1L,
                authenticationCode = ""
            )

        val updateMemberRequest: UpdateMemberRequest
            get() = UpdateMemberRequest(
                name = "updated name",
                verificationPassword = Password("1q2w3e4r")
            )

    }
}