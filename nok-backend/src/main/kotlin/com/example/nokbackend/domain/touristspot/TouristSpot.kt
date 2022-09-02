package com.example.nokbackend.domain.touristspot

import com.example.nokbackend.infra.BaseEntity
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Entity

@Entity
class TouristSpot(
    val name: String,

    val type: String,

    val roadNameAddress: String,

    val landNumberAddress: String,

    val latitude: BigDecimal,

    val longitude: BigDecimal,

    val area: Int,

    val publicBenefitFacilities: String,

    val accommodation: String?,

    val amusement: String,

    val recreationalFacilities: String,

    val customerServiceFacilities: String,

    val supportingFacilities: String?,

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