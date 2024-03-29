package com.example.nokbackend.domain.store

import com.example.nokbackend.application.store.FindStoreCondition
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository


@Repository
class StoreQueryRepository(
    private val queryFactory: SpringDataQueryFactory
) {

    fun findByCondition(storeIds: List<Long>, findStoreCondition: FindStoreCondition): List<Store> {
        val (name, category) = findStoreCondition

        return queryFactory.listQuery {
            select(entity(Store::class))
            from(entity(Store::class))
            associate(Store::class, StoreInformation::class, on(Store::storeInformation))
            whereAnd(
                column(Store::id).`in`(storeIds.queryTrim()),
                category?.run { column(StoreInformation::category).equal(this) },
                name?.run { column(StoreInformation::name).like("%${this.trim()}%") },
                column(Store::status).equal(Store.Status.ACTIVE)
            )
            orderBy(column(Store::id).desc())
        }
    }

    private fun List<Long>.queryTrim(): List<Long> = this.ifEmpty { listOf(0L) }
}