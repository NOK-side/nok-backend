package com.example.nokbackend.application

import com.example.nokbackend.domain.store.Menu
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreImage
import com.example.nokbackend.domain.store.StoreInformation
import java.math.BigDecimal
import javax.persistence.Lob

data class RegisterStoreRequest(
    val owner: RegisterMemberRequest,
    val storeInformation: StoreInformation,
    val menus: List<RegisterMenuRequest> = listOf()
) {
    fun toEntity(ownerId: Long): Store {
        return Store(
            ownerId = ownerId,
            storeInformation = storeInformation,
            status = Store.Status.ACTIVE // todo : 어드민 관련 기능 추가되면 제거
        )
    }
}

data class RegisterMenuRequest(
    var name: String,
    var price: BigDecimal,
    var description: String,
) {
    fun toEntity(store: Store, menuImageUrl: String): Menu {
        return Menu(
            name = name,
            price = price,
            description = description,
            imageUrl = menuImageUrl,
            store = store
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
    val storeInformation: StoreInformation,
    val storeImageUrls: List<String>,
    val menus: List<MenuResponse>
) {
    constructor(store: Store, storeImages: List<StoreImage>, menus: List<Menu>) : this(
        storeInformation = store.storeInformation,
        storeImageUrls = storeImages.map { it.imageUrl },
        menus = menus.map { MenuResponse(it) }
    )
}

data class MenuResponse(
    val name: String,
    val price: BigDecimal,
    val description: String,
    val imageUrl: String,
) {
    constructor(menu: Menu) : this(
        name = menu.name,
        price = menu.price,
        description = menu.description,
        imageUrl = menu.imageUrl
    )
}