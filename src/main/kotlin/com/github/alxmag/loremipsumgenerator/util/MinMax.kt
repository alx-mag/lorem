package com.github.alxmag.loremipsumgenerator.util

data class MinMax(var min: Int, var max: Int) {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(0, 0)

    constructor(both: Int) : this(both, both)

    fun map(transformBoth: (Int) -> Int) = map(transformBoth, transformBoth)

    fun map(transformMin: (Int) -> Int, transformMax: (Int) -> Int) = MinMax(transformMin(min), transformMax(max))

    fun map(other: MinMax, transform: (my: Int, other: Int) -> Int): MinMax {
        val results = sequenceOf(
            transform(min, other.max),
            transform(min, other.min),
            transform(max, other.max),
            transform(max, other.min)
        )

        return MinMax(results.minOf { it }, results.maxOf { it })
    }

    override fun toString(): String = "min=$min, max=$max"
}