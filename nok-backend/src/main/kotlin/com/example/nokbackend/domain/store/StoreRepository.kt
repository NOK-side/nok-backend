package com.example.nokbackend.domain.store

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun StoreRepository.findByIdCheck(id: Long): Store = findByIdOrNull(id) ?: throw RuntimeException("상점이 존재하지 않습니다")

fun StoreRepository.findByIdAndStatusCheck(id: Long, status: Store.Status): Store = findByIdAndStatus(id, status) ?: throw RuntimeException("상점이 존재하지 않습니다")

fun StoreRepository.findByOwnerIdCheck(ownerId: Long): Store = findByOwnerId(ownerId) ?: throw RuntimeException("상점이 존재하지 않습니다")

interface StoreRepository : JpaRepository<Store, Long> {
    fun findByOwnerId(id: Long): Store?

    fun findByIdAndStatus(id: Long, status: Store.Status): Store?
}