package com.example.nokbackend.util

import com.google.gson.GsonBuilder

fun Any.toJsonString(withNull: Boolean = false): String {
    return try {
        val builder = GsonBuilder().disableHtmlEscaping()
            .setPrettyPrinting()

        if(withNull) builder.serializeNulls()

        builder.create().toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        "Fail to print toJsonString()"
    }
}