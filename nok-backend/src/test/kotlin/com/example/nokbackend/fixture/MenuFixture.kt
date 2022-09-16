package com.example.nokbackend.fixture

import com.example.nokbackend.domain.model.store.Menu
import java.math.BigDecimal

const val menuName: String = "testMenu"
val menuPrice: BigDecimal = BigDecimal(1000)
const val menuDescription = "test menu description"

fun aMenu(): Menu = Menu(
    name = menuName,
    price = menuPrice,
    description = menuDescription,
    imageUrl = "",
    status = Menu.Status.ACTIVE,
    store = aStore()
)