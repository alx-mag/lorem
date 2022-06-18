package com.github.alxmag.loremipsumgenerator.util

import com.intellij.ui.ColoredListCellRenderer
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nls.Capitalization.Sentence
import javax.swing.JList

enum class TextAmountUnit(@Nls(capitalization = Sentence) val visibleName: String) : Comparable<TextAmountUnit> {
    CHARACTER("Symbols"),
    WORD("Words"),
    SENTENCE("Sentences"),
    PARAGRAPH("Paragraphs");

    fun visibleNameLabel() = visibleName.lowercase()

    fun parentUnit() = values().getOrNull(ordinal + 1)

    fun childUnit() = values().getOrNull(ordinal - 1)

    class ListCellRenderer : ColoredListCellRenderer<TextAmountUnit>() {
        override fun customizeCellRenderer(
            list: JList<out TextAmountUnit>,
            value: TextAmountUnit?,
            index: Int,
            selected: Boolean,
            hasFocus: Boolean
        ) {
            value ?: return
            append(value.visibleName)
        }
    }
}

fun TextAmountUnit.requireChildUnit() = childUnit() ?: throw IllegalStateException("Unit $this have no children")
fun TextAmountUnit.requireParentUnit() = parentUnit() ?: throw IllegalStateException("Unit $this have no parents")