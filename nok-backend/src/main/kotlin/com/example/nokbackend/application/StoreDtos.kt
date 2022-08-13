package com.example.nokbackend.application

import com.example.nokbackend.domain.store.Address
import com.example.nokbackend.domain.store.Menu
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreInformation
import java.math.BigDecimal

data class RegisterStoreRequest(
    val owner: RegisterMemberRequest,
    val storeInformation: StoreInformation,
    val menus: List<RegisterMenuRequest> = listOf()
) {
    fun toEntity(ownerId: Long): Store {
        return Store(
            ownerId = ownerId,
            storeInformation = storeInformation
        )
    }
}

data class RegisterMenuRequest(
    var name: String,
    var price: BigDecimal,
    var description: String,
    var imageUrl: String
) {
    fun toEntity(store: Store): Menu {
        return Menu(
            name, price, description, imageUrl, store
        )
    }
}


data class FindStoreCondition(
    val name: String?,
    val category: StoreInformation.Category?
)

data class StoreResponse(
    val name: String,
    val category: StoreInformation.Category
) {
    constructor(store: Store) : this(
        name = store.name,
        category = store.category
    )
}

data class StoreDetailResponse(
    val name: String,
    val address: Address,
    val category: StoreInformation.Category
) {
    constructor(store: Store) : this(
        name = store.name,
        address = store.address,
        category = store.category
    )
}