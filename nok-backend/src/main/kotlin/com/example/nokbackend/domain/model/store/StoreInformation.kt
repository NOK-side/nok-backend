package com.example.nokbackend.domain.model.store

import javax.persistence.*

@Embeddable
class StoreInformation(
    @Column
    val businessNumber: String,

    @Column
    var name: String,

    @Column
    @Enumerated(value = EnumType.STRING)
    var category: Category,

    @Column
    var phoneNumber: String,

    @Embedded
    var address: Address,

    @Column
    var imageUrl: String,

    @Lob
    var description: String,

    @Column
    var keyword: String,

    @Embedded
    var businessHour: BusinessHour,

    @Column
    var holidays: String
) {

    enum class Category { DEFAULT, CAFE, RESTAURANT }
}