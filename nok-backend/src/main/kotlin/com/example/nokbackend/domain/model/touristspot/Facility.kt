package com.example.nokbackend.domain.model.touristspot

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Facility(
    @Column
    val publicBenefitFacilities: String?,

    @Column
    val accommodation: String?,

    @Column
    val amusement: String?,

    @Column
    val recreationalFacilities: String?,

    @Column
    val customerServiceFacilities: String?,

    @Column
    val supportingFacilities: String?,
)