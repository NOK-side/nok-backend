package com.example.nokbackend.application

import com.example.nokbackend.domain.store.Menu
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreImage
import com.example.nokbackend.domain.store.StoreInformation
import com.example.nokbackend.util.DmlStatus
import java.math.BigDecimal

data class RegisterStoreRequest(
    val owner: RegisterMemberRequest,
    val storeInformation: StoreInformation,
    val storeImages: List<String>,
    val menus: List<CommonMenuRequest> = listOf()
) {
    fun toEntity(ownerId: Long): Store {
        return Store(
            ownerId = ownerId,
            storeInformation = storeInformation,
            status = Store.Status.ACTIVE // todo : 어드민 관련 기능 추가되면 제거
        )
    }
}

data class CommonMenuRequest(
    val id: Long?,
    val name: String,
    val price: BigDecimal,
    val description: String,
    val imageUrl: String,
    val dmlStatus: DmlStatus = DmlStatus.NOTHING
) {
    fun toEntity(store: Store): Menu {
        return Menu(
            name = name,
            price = price,
            description = description,
            imageUrl = imageUrl,
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

data class UpdateStoreInformationRequest(
    val storeInformation: StoreInformation
)
