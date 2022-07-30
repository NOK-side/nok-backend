package com.example.nokbackend.application

import com.example.nokbackend.domain.store.Address
import com.example.nokbackend.domain.store.Store

data class RegisterStoreRequest(
    val name: String,
    val address: Address,
    val category: Store.Category
) {
    fun toEntity(ownerId: Long): Store {
        return Store(
            ownerId = ownerId,
            name = name,
            address = address,
            category = category
        )
    }
}