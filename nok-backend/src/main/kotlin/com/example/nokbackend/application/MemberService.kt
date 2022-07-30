package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.*
import com.example.nokbackend.util.createRandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationService: AuthenticationService,
    private val mailService: MailService,
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

    //TODO: 로직 추가 고민 필요
    fun findMemberEmail(findMemberEmailRequest: FindMemberEmailRequest): FindMemberEmailResponse {
        val member = memberRepository.findByNameAndPhoneNumber(
            findMemberEmailRequest.name,
            findMemberEmailRequest.phoneNumber
        )

        return FindMemberEmailResponse(email = member.email)
    }

    fun findMemberPassword(findMemberPasswordRequest: FindMemberPasswordRequest): FindMemberPasswordResponse {
        val (email, name) = findMemberPasswordRequest
        val member = memberRepository.findByEmailCheck(email)
        check(member.name == name) { "이름이 일치하지 않습니다" }
        val authentication = authenticationService.create(email, Authentication.Type.FIND_PW)
        mailService.sendMail(MailSendInfo(email, "비밀번호 찾기 인증코드", message = authentication.code))
        return FindMemberPasswordResponse(authentication.id)
    }

    fun initMemberPasswordCheck(initMemberPasswordRequest: InitMemberPasswordRequest) {
        val (authId, email, code) = initMemberPasswordRequest
        authenticationService.check(ConfirmAuthenticationRequest(id = authId, email, code), Authentication.Type.FIND_PW)
    }

    fun initMemberPassword(initMemberPasswordRequest: InitMemberPasswordRequest) {
        val (authId, email, code) = initMemberPasswordRequest
        authenticationService.confirm(ConfirmAuthenticationRequest(id = authId, email, code), Authentication.Type.FIND_PW)
        val password = memberRepository.findByEmailCheck(email)
            .resetPassword(Password(createRandomString(10)))
        mailService.sendMail(MailSendInfo(email, "비밀번호 초기화", message = password.value))
    }

}