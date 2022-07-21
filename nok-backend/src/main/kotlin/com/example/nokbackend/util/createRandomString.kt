package com.example.nokbackend.util

fun createRandomString(length: Int): String {
    val charList: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return (0 until length)
        .map { kotlin.random.Random.nextInt(0, charList.size) }
        .map(charList::get)
        .joinToString("")
}