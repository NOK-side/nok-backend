package com.example.nokbackend.util

sealed class Dml

object Register : Dml()
object Update : Dml()
object Delete : Dml()
object Nothing: Dml()

enum class DmlStatus(
    val dml: Dml
) {
    REGISTER(Register), UPDATE(Update), DELETE(Delete), NOTHING(Nothing);
}
