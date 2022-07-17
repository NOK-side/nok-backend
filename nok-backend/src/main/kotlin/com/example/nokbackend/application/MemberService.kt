package com.example.nokbackend.application

import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {

}