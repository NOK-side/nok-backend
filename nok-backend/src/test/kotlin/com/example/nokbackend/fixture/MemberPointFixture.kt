package com.example.nokbackend.fixture

import com.example.nokbackend.domain.memberpoint.MemberPoint
import java.math.BigDecimal

fun aMemberPoint(
    id: Long = 1L,
    memberId: Long = aMember().id,
    point: BigDecimal = BigDecimal.valueOf(1_000_000),
): MemberPoint = MemberPoint(
    id = id,
    memberId = memberId,
    point = point
)