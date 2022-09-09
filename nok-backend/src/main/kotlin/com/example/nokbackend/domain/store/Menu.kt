package com.example.nokbackend.domain.store

import com.example.nokbackend.application.UpdateMenuRequest
import com.example.nokbackend.infra.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity
class Menu(
    var name: String,

    var price: BigDecimal,

    @Lob
    var description: String,

    var imageUrl: String,

    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.ACTIVE,

    @ManyToOne
    var store: Store,

    id: Long = 0L
) : BaseEntity(id) {
    fun update(updateMenuRequest: UpdateMenuRequest) {
        this.name = updateMenuRequest.name
        this.price = updateMenuRequest.price
        this.description = updateMenuRequest.description
        this.imageUrl = updateMenuRequest.imageUrl
    }

    fun inactive() {
        this.status = Status.INACTIVE
    }

    enum class Status { ACTIVE, INACTIVE }
}