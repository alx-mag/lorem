package com.github.alxmag.loremipsumgenerator.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RandomUtilsTest {

    @Test
    fun numberToTerms() {
        val result = RandomUtils.numberToTerms(29, 5, 7)
        assertTrue(result.size >= 29 / 5)
        assertTrue(result.sum() == 29)
    }
}