package com.example.nokbackend.application.point

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberpoint.MemberPoint
import com.example.nokbackend.domain.memberpoint.MemberPointCharge
import com.example.nokbackend.domain.memberpoint.MemberPointChargeRepository
import com.example.nokbackend.domain.memberpoint.MemberPointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class PointService(
    private val memberPointRepository: MemberPointRepository,
    private val memberPointChargeRepository: MemberPointChargeRepository,
) {

    fun chargePoint(member: Member, chargePointRequest: ChargePointRequest) {
        val memberPoint = memberPointRepository.findByMemberId(member.id)
            ?: memberPointRepository.save(MemberPoint(member.id, BigDecimal.ZERO))

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
}