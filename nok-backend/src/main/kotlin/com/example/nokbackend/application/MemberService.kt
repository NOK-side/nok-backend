package com.example.nokbackend.application

import com.example.nokbackend.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun generateTokenWithRegister(registerMemberRequest: RegisterMemberRequest) {
        val member = registerMemberRequest.toEntity()
        memberRepository.save(member)
    }
}