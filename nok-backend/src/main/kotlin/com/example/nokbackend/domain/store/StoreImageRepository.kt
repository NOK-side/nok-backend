package com.example.nokbackend.domain.store

import org.springframework.data.jpa.repository.JpaRepository

interface StoreImageRepository : JpaRepository<StoreImage, Long> {

    fun findByStoreAndStatus(store: Store, status: StoreImage.Status): List<StoreImage>

    fun findByStoreInAndStatus(stores: List<Store>, status: StoreImage.Status): List<StoreImage>
}