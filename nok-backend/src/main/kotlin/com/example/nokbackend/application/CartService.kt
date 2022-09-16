package com.example.nokbackend.application

import com.example.nokbackend.domain.model.BaseEntityUtil
import com.example.nokbackend.domain.model.cart.CartRepository
import com.example.nokbackend.domain.model.cart.findByIdCheck
import com.example.nokbackend.domain.model.gifticon.Gifticon
import com.example.nokbackend.domain.model.gifticon.GifticonRepository
import com.example.nokbackend.domain.model.member.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CartService(
    private val cartRepository: CartRepository,
    private val gifticonRepository: GifticonRepository
) {

    fun findMyCart(member: Member): List<CartResponse> {
        val carts = cartRepository.findByOwnerId(member.id)

        val gifticonIds = carts.map { it.gifticonId }
        val gifticons = gifticonRepository.findAllById(gifticonIds)
        val gifticonMap = BaseEntityUtil<Gifticon>().mapById(gifticons)

        return carts.map {
            CartResponse(gifticonMap[it.gifticonId]!!, it)
        }
    }

    fun registerItemToCart(member: Member, registerItemToCartRequest: RegisterItemToCartRequest) {
        val cart = registerItemToCartRequest.toEntity(member)
        cartRepository.save(cart)
    }

    fun changeQuantityOfCart(member: Member, changeQuantityOfCartRequest: ChangeQuantityOfCartRequest) {
        val cart = cartRepository.findByIdCheck(changeQuantityOfCartRequest.cartId)
        check(cart.ownerId == member.id) { "본인의 카트만 수정할 수 있습니다" }

        cart.updateQuantity(changeQuantityOfCartRequest.quantity)
    }

    fun deleteItemFromCart(member: Member, cartId: Long) {
        val cart = cartRepository.findByIdCheck(cartId)
        check(cart.ownerId == member.id) { "본인의 카트만 삭제할 수 있습니다" }

        cartRepository.deleteById(cartId)
    }

    fun flushCart(member: Member) {
        val carts = cartRepository.findByOwnerId(member.id)
        cartRepository.deleteAll(carts)
    }
}