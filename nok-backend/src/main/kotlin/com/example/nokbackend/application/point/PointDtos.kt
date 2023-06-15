package com.example.nokbackend.application.point

import com.example.nokbackend.domain.memberpoint.MemberPointCharge
import java.math.BigDecimal

data class ChargePointRequest(
    val token: String,
    val googleOrderId: String,
    val point: BigDecimal,
    val payMethod: MemberPointCharge.PayMethod,
    val payInfo: String,
)
