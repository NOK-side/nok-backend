package com.example.nokbackend.domain.store

import com.example.nokbackend.infra.BaseEntity
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.ManyToOne

@Entity
class Menu(
    var name: String,

    var price: BigDecimal,

    @Lob
    var description: String,

    var imageUrl: String,

    @ManyToOne
    var store: Store,

    id: Long = 0L
) : BaseEntity(id) {
}