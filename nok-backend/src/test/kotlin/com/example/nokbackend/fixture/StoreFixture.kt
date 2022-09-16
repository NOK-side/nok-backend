package com.example.nokbackend.fixture

import com.example.nokbackend.domain.store.*

const val storeBusinessNumber: String = "ABCDEFG"
const val storeName: String = "test store"
val storeCategory: StoreInformation.Category = StoreInformation.Category.RESTAURANT
const val storePhoneNumber: String = "010-1111-2222"
var storeAddress: Address = Address("test address")
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
    address: Address = storeAddress,
    imageUrl: String = storeImageUrl,
    description: String = storeDescription,
    keyword: String = storeKeyword,
    businessHour: BusinessHour = storeBusinessHour,
    holidays: String = storeHolidays
) = StoreInformation(
    businessNumber = businessNumber,
    name = name,
    category = category,
    phoneNumber = phoneNumber,
    address = address,
    imageUrl = imageUrl,
    description = description,
    keyword = keyword,
    businessHour = businessHour,
    holidays = holidays
)

fun aStoreImage(): StoreImage = StoreImage(
    store = aStore(),
    imageUrl = "",
    status = StoreImage.Status.ACTIVE,
    id = 1L
)
