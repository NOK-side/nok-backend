package com.example.nokbackend.domain.cart

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Cart(
    @Column
    val ownerId: Long,

    @Column
    val gifticonId: Long,

    @Column
    var quantity: Int,

    id: Long = 0L
) : BaseEntity(id) {
    fun updateQuantity(quantity: Int) {
        this.quantity = quantity
    }
}