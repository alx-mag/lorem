package com.github.alxmag.loremipsumgenerator.util

import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nls.Capitalization.Sentence

enum class TextAmountUnit(@Nls(capitalization = Sentence) val visibleName: String) : Comparable<TextAmountUnit> {
    CHARACTER("Symbol"),
    WORD("Word"),
    SENTENCE("Sentence"),
    PARAGRAPH("Paragraph");

    fun visibleNameLabel() = visibleName.lowercase()

    fun parentUnit() = values().getOrNull(ordinal + 1)

    fun childUnit() = values().getOrNull(ordinal - 1)

    fun visibleName(plural: Boolean = false) = if (plural) {
        visibleName + "s"
    } else {
        visibleName
    }
}

fun TextAmountUnit.requireChildUnit() = childUnit() ?: throw IllegalStateException("Unit $this have no children")
fun TextAmountUnit.requireParentUnit() = parentUnit() ?: throw IllegalStateException("Unit $this have no parents")

fun TextAmountUnit.getAmountUnits() = TextAmountUnit.values().filter { it < this }