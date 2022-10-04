package com.example.nokbackend.fixture

import com.example.nokbackend.domain.Location
import com.example.nokbackend.domain.store.BusinessHour
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreImage
import com.example.nokbackend.domain.store.StoreInformation
import java.math.BigDecimal

const val storeBusinessNumber: String = "ABCDEFG"
const val storeName: String = "test store"
val storeCategory: StoreInformation.Category = StoreInformation.Category.RESTAURANT
const val storePhoneNumber: String = "010-1111-2222"
var storeLocation: Location = Location("도로명 주소", "지번 주소", BigDecimal(100), BigDecimal(100))
const val storeImageUrl: String = ""
const val storeDescription: String = "test description"
const val storeKeyword: String = "PASTA"
var storeBusinessHour: BusinessHour = BusinessHour(9, 18)
const val storeHolidays: String = "SUNDAY"

fun aStore(): Store = Store(
    1L,
    aStoreInformation()
)

fun aStoreInformation(
    businessNumber: String = storeBusinessNumber,
    name: String = storeName,
    category: StoreInformation.Category = storeCategory,
    phoneNumber: String = storePhoneNumber,
    address: Location = storeLocation,
    description: String = storeDescription,
    keyword: String = storeKeyword,
    businessHour: BusinessHour = storeBusinessHour,
    holidays: String = storeHolidays,
    menuPictureUrl: String = ""
) = StoreInformation(
    businessNumber = businessNumber,
    name = name,
    category = category,
    phoneNumber = phoneNumber,
    location = address,
    description = description,
    keyword = keyword,
    businessHour = businessHour,
    holidays = holidays,
    menuPictureUrl = menuPictureUrl
)

fun aStoreImage(): StoreImage = StoreImage(
    store = aStore(),
    imageUrl = "",
    status = StoreImage.Status.ACTIVE,
    id = 1L
)
