package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationService: AuthenticationService,
    private val uuidGenerator: UUIDGenerator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun updateMemberInfo(member: Member, updateMemberRequest: UpdateMemberRequest): UpdateMemberResponse {
        member.update(updateMemberRequest)
        return UpdateMemberResponse("회원정보가 수정되었습니다.")
    }

    fun updatePassword(member: Member, updatePasswordRequest: UpdatePasswordRequest): UpdatePasswordResponse {
        member.updatePassword(updatePasswordRequest)
        return UpdatePasswordResponse("비밀번호가 변경되었습니다.")
    }

    fun withdraw(member: Member, withdrawMemberRequest: WithdrawMemberRequest): WithdrawMemberResponse {
        member.authenticate(withdrawMemberRequest.password)
        member.inActivate()
        return WithdrawMemberResponse("회원 탈퇴하였습니다.")
    }

    fun findMemberEmail(findMemberIdRequest: FindMemberIdRequest): FindMemberIdResponse {
        val reqNumber = findMemberIdRequest.phoneNumber

        check(memberRepository.existByPhoneNumber(reqNumber)) {
            "등록되지 않은 전화번호입니다"
        }

        val member = memberRepository.findByPhoneNumber(
            reqNumber
        )

//        applicationEventPublisher.publishEvent(MailEvent(member.email, "회원 아이디 찾기", member.memberId))

        return FindMemberIdResponse(memberId = member.memberId )
    }

    fun findMemberPassword(findMemberPasswordRequest: FindMemberPasswordRequest): FindMemberPasswordResponse {
        val reqId = findMemberPasswordRequest.memberId

        check(memberRepository.existByMemberId(reqId)) { "등록되지 않은 아이디입니다." }

        val member = memberRepository.findByMemberIdCheck(reqId)

        val randomPassword = uuidGenerator.generate(8)

        member.newPassword(Password(randomPassword))

        applicationEventPublisher.publishEvent(MailEvent(member.email, "비밀번호 초기화", randomPassword))

        return FindMemberPasswordResponse("등록된 이메일 ( "+ member.email + " ) 로 임시 비밀번호를 발송하였습니다.")
    }


    fun checkEmailDuplication(email: String) {
        check(!memberRepository.existByEmail(email)) { "이미 등록된 이메일입니다" }
    }

    fun checkMemberIdDuplication(memberId: String) {
        check(!memberRepository.existByMemberId(memberId)) { "이미 등록된 아이디입니다" }
    }

}