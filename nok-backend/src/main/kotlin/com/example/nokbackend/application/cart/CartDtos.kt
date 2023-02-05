package com.example.nokbackend.application.cart

import com.example.nokbackend.domain.cart.Cart
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.member.Member
import java.math.BigDecimal

data class RegisterItemToCartRequest(
    val gifticonId: Long,
    val quantity: Int
) {
    fun toEntity(member: Member): Cart {
        return Cart(member.id, gifticonId, quantity)
    }
}

data class ChangeQuantityOfCartRequest(
    val cartId: Long,
    val quantity: Int
)

data class DeleteItemFromCartRequest(
    val cartId: Long,
    val quantity: Int
)

data class CartResponse(
    val cartId: Long,
    val gifticonId: Long,
    val ownerId: Long,
    val gifticonName: String,
    val price: BigDecimal,
    val category: Gifticon.Category,
    val quantity: Int
) {
    constructor(gifticon: Gifticon, cart: Cart) : this(
        cart.id,
        cart.gifticonId,
        cart.ownerId,
        gifticon.productName,
        gifticon.price,
        gifticon.category,
        cart.quantity
    )
}