package com.example.nokbackend.application

import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreImage
import com.example.nokbackend.domain.store.StoreInformation
import java.math.BigDecimal

data class RegisterStoreRequest(
    val owner: RegisterMemberRequest,
    val storeInformation: StoreInformation,
    val storeImages: List<String>
) {
    fun toEntity(ownerId: Long): Store {
        return Store(
            ownerId = ownerId,
            storeInformation = storeInformation,
            status = Store.Status.ACTIVE // todo : 어드민 관련 기능 추가되면 제거
        )
    }
}

data class RegisterStoreResponse(
    val storeId: Long
)

data class FindStoreCondition(
    val name: String?,
    val category: StoreInformation.Category?
)

data class StoreResponse(
    val id: Long,
    val name: String,
    val category: StoreInformation.Category
) {
    constructor(store: Store) : this(
        id = store.id,
        name = store.name,
        category = store.category
    )
}

data class StoreDetailResponse(
    val storeInformation: StoreInformation,
    val storeImageUrls: List<String>,
) {
    constructor(store: Store, storeImages: List<StoreImage>) : this(
        storeInformation = store.storeInformation,
        storeImageUrls = storeImages.map { it.imageUrl },
    )
}

data class UpdateStoreInformationRequest(
    val storeInformation: StoreInformation
)
