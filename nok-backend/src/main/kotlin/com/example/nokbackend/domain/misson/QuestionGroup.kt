package com.example.nokbackend.domain.misson

import com.example.nokbackend.application.Answer
import com.example.nokbackend.domain.BaseEntity
import org.hibernate.annotations.LazyToOne
import org.hibernate.annotations.LazyToOneOption
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class QuestionGroup(
    @OneToOne
    @LazyToOne(value = LazyToOneOption.PROXY)
    val mission: Mission,

    var title: String,

    var description: String,

    id: Long = 0L
) : BaseEntity(id) {
}

fun List<Question>.grade(answers: List<Answer>): Int {
    val answerMap = hashMapOf<Long, Int>()

    answers.forEach { answerMap[it.questionId] = it.answer }

    return count { it.mark(answerMap[it.id]!!) }
}