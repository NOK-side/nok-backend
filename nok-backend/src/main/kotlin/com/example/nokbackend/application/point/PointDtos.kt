package com.example.nokbackend.application.point

import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.memberpoint.MemberPointCharge

data class ChargePointRequest(
    val token: String,
    val googleOrderId: String,
    val point: Point,
    val payMethod: MemberPointCharge.PayMethod,
    val payInfo: String,
)
