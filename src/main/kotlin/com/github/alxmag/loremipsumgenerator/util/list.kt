package com.github.alxmag.loremipsumgenerator.util

fun <T> List<T>.removeDuplicates(): List<T> {
    val seen = mutableSetOf<T>()
    return fold(mutableListOf()) { result, item ->
        if (!seen.contains(item)) {
            result.add(item)
            seen.add(item)
        }
        result
    }
}