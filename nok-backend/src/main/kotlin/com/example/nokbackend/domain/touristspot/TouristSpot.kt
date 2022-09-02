package com.example.nokbackend.domain.touristspot

import com.example.nokbackend.infra.BaseEntity
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class TouristSpot(
    val name: String,

    val type: String,

    @Embedded
    val location: Location,

    val area: Int,

    @Embedded
    val facility: Facility,

    val registerDate: LocalDate,

    val numberOfCapacity: Int,

    val numberOfParking: Int,

    val description: String,

    val managementAgencyPhoneNumber: String,

    val managementAgencyName: String,

    val dataBaseDate: LocalDate,

    id: Long = 0L
) : BaseEntity(id) {
}