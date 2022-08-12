package com.example.nokbackend.domain.store

import com.example.nokbackend.infra.BaseEntity
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Store(
    val ownerId: Long,

    @Embedded
    var storeInformation: StoreInformation,

    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.READY,

    id: Long = 0L
) : BaseEntity(id) {
    val businessNumber: String
        get() = storeInformation.businessNumber

    val name: String
        get() = storeInformation.name

    val category: StoreInformation.Category
        get() = storeInformation.category

    val phoneNumber: String
        get() = storeInformation.phoneNumber

    val address: Address
        get() = storeInformation.address

    val imageUrl: String
        get() = storeInformation.imageUrl

    val description: String
        get() = storeInformation.description

    val keyword: String
        get() = storeInformation.keyword

    val businessHour: BusinessHour
        get() = storeInformation.businessHour

    val holidays: String
        get() = storeInformation.holidays


    enum class Status { READY, ACTIVE, INACTIVE, }
}