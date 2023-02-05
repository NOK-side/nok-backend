package com.example.nokbackend.application.store

import com.example.nokbackend.application.util.centerOfKoreaLatitude
import com.example.nokbackend.application.util.centerOfKoreaLongitude
import com.example.nokbackend.application.util.defaultDistance
import com.example.nokbackend.application.member.RegisterMemberRequest
import com.example.nokbackend.domain.member.Member
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
    val category: StoreInformation.Category?,
    val longitude: BigDecimal = centerOfKoreaLongitude,
    val latitude: BigDecimal = centerOfKoreaLatitude,
    val distance: Int = defaultDistance
)

data class StoreResponse(
    val id: Long,
    val name: String,
    val category: StoreInformation.Category,
    val keywords: List<String>,
    val description: String,
    val imageUrl: String
) {
    constructor(store: Store, storeImage: StoreImage?) : this(
        id = store.id,
        name = store.name,
        category = store.category,
        keywords = store.keyword.split("|"),
        description = store.description,
        imageUrl = storeImage?.imageUrl ?: ""
    )
}

data class StoreDetailResponse(
    val storeInformation: StoreInformation,
    val storeImageUrls: List<String>,
    val ownerResponse: OwnerResponse
) {
    constructor(store: Store, storeImages: List<StoreImage>, owner: Member) : this(
        storeInformation = store.storeInformation,
        storeImageUrls = storeImages.map { it.imageUrl },
        OwnerResponse(owner.id, owner.name, owner.profileImage)
    )
}

data class OwnerResponse(
    val id: Long,
    val name: String,
    val profileImage: String
)

data class UpdateStoreInformationRequest(
    val storeInformation: StoreInformation
)

data class Answer(
    val questionId: Long,
    val answer: Int
)