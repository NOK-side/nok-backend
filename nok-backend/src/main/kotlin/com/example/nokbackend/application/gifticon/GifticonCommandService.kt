package com.example.nokbackend.application.gifticon

import com.example.nokbackend.application.util.UUIDGenerator
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByOwnerIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
@Transactional
class GifticonCommandService(
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository,
    private val uuidGenerator: UUIDGenerator,
    private val gifticonIdLength: Int = 8
) {
    fun registerGifticon(member: Member, registerGifticonRequest: RegisterGifticonRequest) {
        check(member.role == Member.Role.STORE) { "상점 주인만 등록할 수 있습니다" }

        val store = storeRepository.findByOwnerIdCheck(member.id)
        val gifticon = registerGifticonRequest.toEntity(store.id, generateGifticonId(store.id))
        gifticonRepository.save(gifticon)
    }

    private fun generateGifticonId(storeId: Long): String {
        val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
        return "${storeId.toString().padStart(5, '0')} + ${LocalDate.now().format(pattern)} + ${
            uuidGenerator.generate(
                gifticonIdLength
            ).uppercase()
        }"
    }
}