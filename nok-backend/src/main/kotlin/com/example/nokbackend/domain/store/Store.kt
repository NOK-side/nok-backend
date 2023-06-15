package com.example.nokbackend.domain.store

import com.example.nokbackend.application.store.UpdateStoreInformationRequest
import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.Location
import javax.persistence.*

@Entity
class Store(
    @Column
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

    val location: Location
        get() = storeInformation.location

    val description: String
        get() = storeInformation.description

    val keyword: String
        get() = storeInformation.keyword

    val businessHour: BusinessHour
        get() = storeInformation.businessHour

    val holidays: String
        get() = storeInformation.holidays

    fun activate() {
        status = Status.ACTIVE
    }

    fun updateStoreInformation(updateStoreInformationRequest: UpdateStoreInformationRequest) {
        storeInformation = updateStoreInformationRequest.storeInformation
    }

    enum class Status { READY, ACTIVE, INACTIVE, }
}