package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.*
import com.example.nokbackend.util.createRandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationService: AuthenticationService,
    private val imageService: ImageService
) {

    fun updateMemberInfo(member: Member, updateMemberRequest: UpdateMemberRequest, profileImage: MultipartFile?) {
        val profileImageUrl = profileImage?.let {
            val uuid = UUID.randomUUID().toString()
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
        val member = memberRepository.findByNameAndPhoneNumber(
            findMemberIdRequest.name,
            findMemberIdRequest.phoneNumber
        )
        //FIXME: 임시 - 메일 보내기 비활성화 떄문에 ㅠㅠ
//        mailService.sendMail(MailSendInfo(member.email, "회원 아이디 찾기", message = member.memberId))
        return FindMemberIdResponse(email = member.email, memberId = member.memberId)
    }

    fun findMemberPassword(findMemberPasswordRequest: FindMemberPasswordRequest): FindMemberPasswordResponse {
        val (email, name) = findMemberPasswordRequest
        val member = memberRepository.findByEmailCheck(email)
        check(member.name == name) { "이름이 일치하지 않습니다" }
        val authentication = authenticationService.registerAuthentication(email, Authentication.Type.FIND_PW)
        //FIXME: 임시 - 메일 보내기 비활성화 떄문에 ㅠㅠ
//        mailService.sendMail(MailSendInfo(email, "비밀번호 찾기 인증코드", message = authentication.code))
        return FindMemberPasswordResponse(authentication.id, authentication.code)
    }

    fun initMemberPasswordCheck(resetMemberPasswordRequest: ResetMemberPasswordRequest) {
        val (authId, email, code) = resetMemberPasswordRequest
        authenticationService.checkAuthentication(ConfirmAuthenticationRequest(id = authId, email, code), Authentication.Type.FIND_PW)
    }

    fun resetMemberPassword(resetMemberPasswordRequest: ResetMemberPasswordRequest): Password {
        val (authId, email, code) = resetMemberPasswordRequest
        authenticationService.confirmAuthentication(
            ConfirmAuthenticationRequest(id = authId, email, code),
            Authentication.Type.FIND_PW
        )
        val randomPassword = Password(createRandomString(10))
        memberRepository.findByEmailCheck(email)
            .updatePassword(randomPassword)
        //FIXME: 임시 - 메일 보내기 비활성화 떄문에 ㅠㅠ
//        mailService.sendMail(MailSendInfo(email, "비밀번호 초기화", initPassword))
        return randomPassword
    }

    fun checkEmailDuplication(email: String) {
        check(!memberRepository.existByEmail(email)) { "이미 등록된 이메일입니다" }
    }

    fun checkMemberIdDuplication(memberId: String) {
        check(!memberRepository.existByMemberId(memberId)) { "이미 등록된 아이디입니다" }
    }

}