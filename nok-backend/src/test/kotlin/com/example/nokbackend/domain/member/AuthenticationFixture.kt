package com.example.nokbackend.domain.member

import com.example.nokbackend.application.ConfirmAuthenticationRequest
import com.example.nokbackend.application.VerifyEmailRequest

class AuthenticationFixture {

    companion object {
        val verifyEmailRequest: VerifyEmailRequest
            get() = VerifyEmailRequest("rkdals213@naver.com")

        val confirmAuthenticationRequest: ConfirmAuthenticationRequest
            get() = ConfirmAuthenticationRequest(1L, "rkdals213@naver.com", "")
    }
}