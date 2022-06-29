package com.github.alxmag.loremipsumgenerator.util

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object RandomUtils {
    fun numberToTerms(sum: Int, termMin: Int, termMax: Int): List<Int> {
        var remainSize = sum
        val result = mutableListOf<Int>()
        while (remainSize > 0) {
            val nextSize = when {
                // if remain size is less than minimum
                remainSize <= termMin -> remainSize
                else -> {
                    val maxSize = if (termMax >= remainSize) remainSize else termMax
                    Random().nextInt(maxSize - termMin) + termMin
                }

            }
            result.add(nextSize)
            remainSize -= nextSize
        }
        return result
    }

    fun getRandomIntBetween(min: Int, max: Int): Int {
        require(max >= min)
        return ThreadLocalRandom.current().nextInt(min, max + 1)
    }
}

fun MinMax.randomBetween() = RandomUtils.getRandomIntBetween(min, max)