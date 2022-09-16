package com.example.nokbackend.application

import com.example.nokbackend.domain.model.cart.Cart
import com.example.nokbackend.domain.model.gifticon.Gifticon
import com.example.nokbackend.domain.model.member.Member
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