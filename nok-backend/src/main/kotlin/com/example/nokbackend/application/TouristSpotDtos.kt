package com.example.nokbackend.application

import com.example.nokbackend.domain.touristspot.Facility
import com.example.nokbackend.domain.touristspot.Location
import com.example.nokbackend.domain.touristspot.TouristSpot
import java.math.BigDecimal
import java.time.LocalDate

data class FindTouristSpotByDistanceRequest(
    val longitude: BigDecimal,
    val latitude: BigDecimal,
    val distance: Int
)

data class FindTouristSpotCondition(
    val name: String?,
    val sido: String?
)

data class FindTouristSpotResponse(
    val id: Long,
    val name: String,
    val type: String,
    val managementAgencyPhoneNumber: String,
) {
    constructor(touristSpot: TouristSpot) : this(
        touristSpot.id,
        touristSpot.name,
        touristSpot.type,
        touristSpot.managementAgencyName
    )
}

data class TouristSpotDetailResponse(
    val id: Long,
    val name: String,
    val type: String,
    val location: Location,
    val area: Int,
    val facility: Facility,
    val registerDate: LocalDate,
    val numberOfCapacity: Int,
    val numberOfParking: Int,
    val description: String,
    val managementAgencyPhoneNumber: String,
    val managementAgencyName: String,
) {
    constructor(touristSpot: TouristSpot) : this(
        id = touristSpot.id,
        name = touristSpot.name,
        type = touristSpot.type,
        location = touristSpot.location,
        area = touristSpot.area,
        facility = touristSpot.facility,
        registerDate = touristSpot.registerDate,
        numberOfCapacity = touristSpot.numberOfCapacity,
        numberOfParking = touristSpot.numberOfParking,
        description = touristSpot.description,
        managementAgencyPhoneNumber = touristSpot.managementAgencyPhoneNumber,
        managementAgencyName = touristSpot.managementAgencyName,
    )
}