package com.example.nokbackend.fixture

import com.example.nokbackend.domain.store.*

const val businessNumber: String = "ABCDEFG"
const val storeName: String = "test store"
val storeCategory: StoreInformation.Category = StoreInformation.Category.RESTAURANT
const val storePhoneNumber: String = "010-1111-2222"
var storeAddress: Address = Address("test address")
const val storeImageUrl: String = ""
const val description: String = "test description"
const val storeKeyword: String = "PASTA"
var businessHour: BusinessHour = BusinessHour(9, 18)
const val holidays: String = "SUNDAY"

fun aStore(): Store = Store(
    1L,
    aStoreInformation()
)

fun aStoreInformation() = StoreInformation(
    businessNumber = businessNumber,
    name = storeName,
    category = storeCategory,
    phoneNumber = storePhoneNumber,
    address = storeAddress,
    imageUrl = storeImageUrl,
    description = description,
    keyword = storeKeyword,
    businessHour = businessHour,
    holidays = holidays
)
