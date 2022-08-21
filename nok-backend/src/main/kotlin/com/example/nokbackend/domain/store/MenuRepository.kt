package com.example.nokbackend.domain.store

import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long> {

    fun findByStoreAndStatus(store: Store, status: Menu.Status): List<Menu>
}