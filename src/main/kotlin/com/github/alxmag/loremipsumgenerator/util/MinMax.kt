package com.github.alxmag.loremipsumgenerator.util

data class MinMax(var min: Int, var max: Int) {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(0, 0)
}