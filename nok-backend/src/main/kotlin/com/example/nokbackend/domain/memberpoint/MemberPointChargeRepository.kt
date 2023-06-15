package com.example.nokbackend.domain.memberpoint

import org.springframework.data.jpa.repository.JpaRepository

interface MemberPointChargeRepository : JpaRepository<MemberPointCharge, Long> {
}