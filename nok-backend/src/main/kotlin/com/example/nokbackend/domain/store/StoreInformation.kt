package com.example.nokbackend.domain.store

import com.example.nokbackend.domain.infra.Location
import javax.persistence.*

@Embeddable
class StoreInformation(
    @Column
    val businessNumber: String,

    @Column
    val name: String,

    @Column
    @Enumerated(value = EnumType.STRING)
    val category: Category,

    @Column
    val phoneNumber: String,

    @Embedded
    val location: Location,

    @Lob
    val description: String,

    @Column
    val keyword: String,

    @Embedded
    val businessHour: BusinessHour,

    @Column
    val holidays: String,

    @Column
    val menuPictureUrl: String
) {
    val keywords: List<String>
        get() = keyword.split("|")

    enum class Category { DEFAULT, CAFE, RESTAURANT }
}