package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationService: AuthenticationService,
    private val imageService: ImageService,
    private val uuidGenerator: UUIDGenerator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun updateMemberInfo(member: Member, updateMemberRequest: UpdateMemberRequest, profileImage: MultipartFile?) {
        val profileImageUrl = profileImage?.let {
            val uuid = uuidGenerator.generate(16)
            imageService.uploadFile(profileImage, uuid)
        }

        member.update(updateMemberRequest, profileImageUrl)
    }

    fun updatePassword(member: Member, updatePasswordRequest: UpdatePasswordRequest) {
        member.updatePassword(updatePasswordRequest)
    }

    fun withdraw(member: Member, withdrawMemberRequest: WithdrawMemberRequest) {
        authenticationService.confirmAuthentication(
            ConfirmAuthenticationRequest(
                withdrawMemberRequest.authenticationId,
                member.email,
                withdrawMemberRequest.authenticationCode
            ),
            Authentication.Type.WITHDRAW
        )
        member.inActivate()
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

        return FindMemberIdResponse(memberId = member.memberId, ResultCode.Success.code)
    }

    fun findMemberPassword(findMemberPasswordRequest: FindMemberPasswordRequest): FindMemberPasswordResponse {
        val reqId = findMemberPasswordRequest.memberId

        check(memberRepository.existByMemberId(reqId)) { "등록되지 않은 아이디입니다." }

        val member = memberRepository.findByMemberIdCheck(reqId)

        val authentication = authenticationService.registerAuthentication(member.email, Authentication.Type.FIND_PW)
        applicationEventPublisher.publishEvent(MailEvent(member.email, "비밀번호 찾기 인증코드", message = authentication.code))

        return FindMemberPasswordResponse(authentication.id, authentication.code)
    }

    fun initMemberPasswordCheck(initMemberPWCheckRequest: InitMemberPWCheckRequest) {
        val (authId, email, code) = initMemberPWCheckRequest
        authenticationService.checkAuthentication(ConfirmAuthenticationRequest(id = authId, email, code), Authentication.Type.FIND_PW)
    }

    fun resetMemberPassword(resetMemberPasswordRequest: ResetMemberPasswordRequest): ResetMemberPasswordResponse {
        val (email) = resetMemberPasswordRequest

//        authenticationService.confirmAuthentication(
//            ConfirmAuthenticationRequest(id = authId, email, code),
//            Authentication.Type.FIND_PW
//        )

        val randomPassword = uuidGenerator.generate(8)
        memberRepository.findByEmailCheck(email)
            .newPassword(Password(randomPassword))

//        applicationEventPublisher.publishEvent(MailEvent(email, "비밀번호 초기화", uuidGenerator.generate(8)))

        return ResetMemberPasswordResponse(randomPassword, ResultCode.Success.code)
    }

    fun checkEmailDuplication(email: String) {
        check(!memberRepository.existByEmail(email)) { "이미 등록된 이메일입니다" }
    }

    fun checkMemberIdDuplication(memberId: String) {
        check(!memberRepository.existByMemberId(memberId)) { "이미 등록된 아이디입니다" }
    }

}