package com.example.nokbackend.domain.store

import com.example.nokbackend.domain.Address
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob

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
    var holidays: String,

    @Column
    var menuPictureUrl: String
) {

    enum class Category { DEFAULT, CAFE, RESTAURANT }
}