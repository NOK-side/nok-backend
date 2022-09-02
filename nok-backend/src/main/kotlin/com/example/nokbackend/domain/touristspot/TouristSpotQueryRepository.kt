package com.example.nokbackend.domain.touristspot

import com.example.nokbackend.application.FindTouristSpotCondition
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository

@Repository
class TouristSpotQueryRepository(
    private val queryFactory: SpringDataQueryFactory
) {

    fun findByCondition(findTouristSpotCondition: FindTouristSpotCondition): List<TouristSpot> {
        val (name, sido) = findTouristSpotCondition

        return queryFactory.listQuery<TouristSpot> {
            select(entity(TouristSpot::class))
            from(entity(TouristSpot::class))
            associate(TouristSpot::class, Location::class, on(TouristSpot::location))
            where(
                and(
                    name?.run { column(TouristSpot::name).like("%${this.trim()}%") },
                    sido?.run { column(Location::landNumberAddress).like("%${this.trim()}%") },
                    sido?.run { column(Location::roadNameAddress).like("%${this.trim()}%") }
                )
            )
        }
    }
}