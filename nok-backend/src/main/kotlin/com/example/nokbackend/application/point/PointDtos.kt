package com.example.nokbackend.application.point

import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.memberpoint.MemberPointCharge
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ChargePointRequest(
    val token: String,
    val googleOrderId: String,
    val point: Point,
    val payMethod: MemberPointCharge.PayMethod,
    val payInfo: String,
)

data class MemberPointResponse(
    val point: Point
)

data class MemberPointChargeResponse(
    val id: Long,
    val token: String,
    val googleOrderId: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var createDate: LocalDateTime,
    val point: Point,
    val payMethod: MemberPointCharge.PayMethod,
    val payInfo: String
) {
    constructor(memberPointCharge: MemberPointCharge) : this(
        id = memberPointCharge.id,
        token = memberPointCharge.token,
        googleOrderId = memberPointCharge.googleOrderId,
        createDate = memberPointCharge.createDate,
        point = memberPointCharge.point,
        payMethod = memberPointCharge.payMethod,
        payInfo = memberPointCharge.payInfo
    )
}