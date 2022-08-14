package com.example.nokbackend.domain.gifticon

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun GifticonRepository.findByIdCheck(id: Long): Gifticon = findByIdOrNull(id) ?: throw RuntimeException("기프티콘이 존재하지 않습니다")

interface GifticonRepository : JpaRepository<Gifticon, Long> {
    fun findByStoreIdAndStatus(storeId: Long, status: Gifticon.Status): List<Gifticon>
}