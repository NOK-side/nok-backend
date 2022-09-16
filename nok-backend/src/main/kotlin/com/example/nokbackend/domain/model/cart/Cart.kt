package com.example.nokbackend.domain.model.cart

import com.example.nokbackend.domain.model.BaseEntity
import javax.persistence.Entity

@Entity
class Cart(
    val ownerId: Long,

    val gifticonId: Long,

    var quantity: Int,

    id: Long = 0L
) : BaseEntity(id) {
    fun updateQuantity(quantity: Int) {
        this.quantity = quantity
    }
}