package com.example.nokbackend.application.cart

import com.example.nokbackend.domain.cart.CartRepository
import com.example.nokbackend.domain.cart.findByIdCheck
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CartCommandService(
    private val cartRepository: CartRepository,
    private val gifticonRepository: GifticonRepository,
) {

    fun registerItemToCart(member: Member, registerItemToCartRequest: RegisterItemToCartRequest) {
        gifticonRepository.findByIdCheck(registerItemToCartRequest.gifticonId)

        val cart = cartRepository.findByOwnerIdAndGifticonId(member.id, registerItemToCartRequest.gifticonId)

        if (cart == null) {
            val newCart = registerItemToCartRequest.toEntity(member)
            cartRepository.save(newCart)
            return
        }

        cart.quantity += registerItemToCartRequest.quantity
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