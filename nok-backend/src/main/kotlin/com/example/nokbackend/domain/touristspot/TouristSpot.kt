package com.example.nokbackend.domain.touristspot

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Location
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class TouristSpot(
    @Column
    val name: String,

    @Column
    val type: String,

    @Embedded
    val location: Location,

    @Column
    val area: Int,

    @Embedded
    val facility: Facility,

    @Column
    val registerDate: LocalDate,

    @Column
    val numberOfCapacity: Int,

    @Column
    val numberOfParking: Int,

    @Column
    val description: String,

    @Column
    val managementAgencyPhoneNumber: String,

    @Column
    val managementAgencyName: String,

    @Column
    val dataBaseDate: LocalDate,

    id: Long = 0L
) : BaseEntity(id) {
}