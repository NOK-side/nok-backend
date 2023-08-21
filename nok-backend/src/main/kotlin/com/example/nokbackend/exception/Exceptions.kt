package com.example.nokbackend.exception

fun verify(value: Boolean, exception: () -> Exception = { RuntimeException() }) {
    if (value) {
        return
    }

    throw exception.invoke()
}