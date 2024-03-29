package com.example.nokbackend.application.point

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberpoint.MemberPointCharge
import com.example.nokbackend.domain.memberpoint.MemberPointChargeRepository
import com.example.nokbackend.domain.memberpoint.MemberPointRepository
import com.example.nokbackend.domain.memberpoint.findByMemberIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PointCommandService(
    private val memberPointRepository: MemberPointRepository,
    private val memberPointChargeRepository: MemberPointChargeRepository,
) {

    fun chargePoint(member: Member, chargePointRequest: ChargePointRequest) {
        val memberPoint = memberPointRepository.findByMemberIdCheck(member.id)

        val memberPointCharge = MemberPointCharge(
            memberPoint = memberPoint,
            token = chargePointRequest.token,
            googleOrderId = chargePointRequest.googleOrderId,
            point = chargePointRequest.point,
            payMethod = MemberPointCharge.PayMethod.CARD,
            payInfo = chargePointRequest.payInfo
        )
        memberPointChargeRepository.save(memberPointCharge)

        memberPoint.point += chargePointRequest.point
    }

    fun spendPoint(member: Member, spendPointRequest: SpendPointRequest) {
        val memberPoint = memberPointRepository.findByMemberIdCheck(member.id)
        check(memberPoint.point >= spendPointRequest.point) { "보유 포인트가 부족합니다" }

        memberPoint.point -= spendPointRequest.point
    }
}