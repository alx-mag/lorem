package com.github.alxmag.loremipsumgenerator.util

/**
 * Divides two integers with rounding result up
 */
internal infix fun Int.divRoundUp(other: Int) = (this + other - 1) / other

