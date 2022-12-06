package com.example.nokbackend.domain.misson

import com.example.nokbackend.application.FindMissionGroupCondition
import com.example.nokbackend.domain.Location
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository

@Repository
class MissionGroupQueryRepository(
    private val queryFactory: SpringDataQueryFactory
) {

    fun findByCondition(missionGroupIds: List<Long>, findMissionGroupCondition: FindMissionGroupCondition): List<MissionGroup> {
        val (city, keyword, _, _, _) = findMissionGroupCondition

        return queryFactory.listQuery {
            select(entity(MissionGroup::class))
            from(entity(MissionGroup::class))
            associate(MissionGroup::class, Location::class, on(MissionGroup::location))
            whereAnd(
                column(MissionGroup::id).`in`(missionGroupIds.queryTrim()),
                city?.run { column(Location::landNumberAddress).like("%${this.trim()}%") },
                city?.run { column(Location::roadNameAddress).like("%${this.trim()}%") },
                keyword?.run { column(MissionGroup::title).like("%${this.trim()}%") },
                keyword?.run { column(MissionGroup::subTitle).like("%${this.trim()}%") },
                keyword?.run { column(MissionGroup::description).like("%${this.trim()}%") },
            )
            orderBy(column(MissionGroup::id).desc())
        }
    }

    private fun List<Long>.queryTrim() : List<Long> = this.ifEmpty { listOf(0L) }

}