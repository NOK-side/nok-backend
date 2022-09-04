package com.example.nokbackend.domain.store

import com.example.nokbackend.application.CommonMenuRequest
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
    fun update(commonMenuRequest: CommonMenuRequest) {
        check(commonMenuRequest.dmlStatus == CommonMenuRequest.DmlStatus.UPDATE) { "요청을 업데이트로 설정해야 합니다" }

        this.name = commonMenuRequest.name
        this.price = commonMenuRequest.price
        this.description = commonMenuRequest.description
        this.imageUrl = commonMenuRequest.imageUrl
    }

    fun inactive() {
        this.status = Status.INACTIVE
    }

    enum class Status { ACTIVE, INACTIVE }
}