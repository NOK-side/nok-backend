package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.Password
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationService: AuthenticationService
) {

    fun updateMemberInfo(member: Member, updateMemberRequest: UpdateMemberRequest) {
        member.update(updateMemberRequest)
    }

    fun updatePassword(member: Member, updatePasswordRequest: UpdatePasswordRequest) {
        member.updatePassword(updatePasswordRequest)
    }

    fun withdraw(member: Member, withdrawMemberRequest: WithdrawMemberRequest) {
        authenticationService.confirm(
            ConfirmAuthenticationRequest(
                withdrawMemberRequest.authenticationId,
                withdrawMemberRequest.email,
                withdrawMemberRequest.authenticationCode
            ),
            Authentication.Type.WITHDRAW
        )

        member.inActivate()
    }

}