package com.example.nokbackend.application.cart

import com.example.nokbackend.domain.cart.CartRepository
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CartQueryService(
    private val cartRepository: CartRepository,
    private val gifticonRepository: GifticonRepository
) {

    fun findMyCart(member: Member): List<CartResponse> {
        val carts = cartRepository.findByOwnerId(member.id)

        val gifticonIds = carts.map { it.gifticonId }
        val gifticons = gifticonRepository.findAllById(gifticonIds)
        val gifticonMap = toHashmapByIdAsKey(gifticons)

        return carts.map { CartResponse(gifticonMap[it.gifticonId]!!, it) }
    }
}