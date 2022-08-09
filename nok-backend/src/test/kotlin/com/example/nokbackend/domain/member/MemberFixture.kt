package com.example.nokbackend.domain.member

import com.example.nokbackend.application.RegisterMemberRequest

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
                role = Member.Role.USER
            )

    }
}