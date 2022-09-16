package com.example.nokbackend.domain.model.store

import org.springframework.data.jpa.repository.JpaRepository

interface StoreImageRepository : JpaRepository<StoreImage, Long> {

    fun findByStoreAndStatus(store: Store, status: StoreImage.Status): List<StoreImage>
}