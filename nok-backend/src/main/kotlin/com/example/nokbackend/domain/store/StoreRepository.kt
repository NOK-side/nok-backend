package com.example.nokbackend.domain.store

import com.example.nokbackend.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun StoreRepository.findByIdCheck(id: Long): Store = findByIdOrNull(id) ?: throw RuntimeException("상점이 존재하지 않습니다")

fun StoreRepository.findByIdOwnerCheck(id: Long, member: Member): Store = findByIdCheck(id).apply {
    check(ownerId == member.id) { "본인의 상점이 아닙니다" }
}

fun StoreRepository.findByOwnerIdCheck(ownerId: Long): Store = findByOwnerId(ownerId) ?: throw RuntimeException("상점이 존재하지 않습니다")

interface StoreRepository : JpaRepository<Store, Long> {
    fun findByOwnerId(id: Long): Store?
}