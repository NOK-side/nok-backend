package com.example.nokbackend.domain.store

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MenuRepository.findByIdCheck(id: Long): Menu = findByIdOrNull(id) ?: throw RuntimeException("메뉴가 존재하지 않습니다")

interface MenuRepository : JpaRepository<Menu, Long> {

    fun findByStoreAndStatus(store: Store, status: Menu.Status): List<Menu>
}