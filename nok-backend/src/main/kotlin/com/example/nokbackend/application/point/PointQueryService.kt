package com.example.nokbackend.application.point

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberpoint.MemberPointChargeRepository
import com.example.nokbackend.domain.memberpoint.MemberPointRepository
import com.example.nokbackend.domain.memberpoint.findByMemberIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointQueryService(
    private val memberPointRepository: MemberPointRepository,
    private val memberPointChargeRepository: MemberPointChargeRepository,
) {

    fun findMyPoint(member: Member): MemberPointResponse {
        val memberPoint = memberPointRepository.findByMemberIdCheck(member.id)
        return MemberPointResponse(memberPoint.point)
    }

    fun findMyPointCharge(member: Member): List<MemberPointChargeResponse> {
        val memberPoint = memberPointRepository.findByMemberIdCheck(member.id)
        return memberPointChargeRepository.findByMemberPoint(memberPoint)
            .map { MemberPointChargeResponse(it) }

    }
}