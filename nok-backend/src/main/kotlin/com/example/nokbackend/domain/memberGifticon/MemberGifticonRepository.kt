package com.example.nokbackend.domain.memberGifticon

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberGifticonRepository.findByIdCheck(id: Long) = findByIdOrNull(id) ?: throw RuntimeException("기프티콘이 존재하지 않습니다")

interface MemberGifticonRepository : JpaRepository<MemberGifticon, Long> {
    fun findByOwnerId(ownerId: Long): List<MemberGifticon>

    fun findByOwnerIdAndStatus(ownerId: Long, status: MemberGifticon.Status): List<MemberGifticon>
}