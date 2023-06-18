package com.example.nokbackend.fixture

import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.memberpoint.MemberPoint

fun aMemberPoint(
    id: Long = 1L,
    memberId: Long = aMember().id,
    point: Point = Point(1_000_000),
): MemberPoint = MemberPoint(
    id = id,
    memberId = memberId,
    point = point
)