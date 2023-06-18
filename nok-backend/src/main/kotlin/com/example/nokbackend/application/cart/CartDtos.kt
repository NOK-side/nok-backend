package com.example.nokbackend.application.cart

import com.example.nokbackend.domain.cart.Cart
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.member.Member

data class RegisterItemToCartRequest(
    val gifticonId: Long,
    val quantity: Int,
) {
    fun toEntity(member: Member): Cart {
        return Cart(
            ownerId = member.id,
            gifticonId = gifticonId,
            quantity = quantity
        )
    }
}

data class ChangeQuantityOfCartRequest(
    val cartId: Long,
    val quantity: Int,
)

data class DeleteItemFromCartRequest(
    val cartId: Long,
    val quantity: Int,
)

data class CartResponse(
    val cartId: Long,
    val gifticonId: Long,
    val ownerId: Long,
    val gifticonName: String,
    val price: Point,
    val category: Gifticon.Category,
    val quantity: Int,
    val imageUrl: String,
) {
    constructor(gifticon: Gifticon, cart: Cart) : this(
        cart.id,
        cart.gifticonId,
        cart.ownerId,
        gifticon.productName,
        gifticon.price,
        gifticon.category,
        cart.quantity,
        gifticon.imageUrl
    )
}