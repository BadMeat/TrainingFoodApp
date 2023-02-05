package com.example.testapp.util

fun Int.formatDecimalSeparator(): String {
    return toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}