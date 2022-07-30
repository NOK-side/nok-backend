package com.example.nokbackend.domain.store

import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long> {
}