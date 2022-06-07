package com.github.alxmag.loremipsumgenerator.util

import com.intellij.ui.ColoredListCellRenderer
import javax.swing.JList

enum class TextAmountUnit(val visibleName: String) {
    CHARACTER("Symbols"),
    WORD("Words"),
    SENTENCE("Sentences"),
    PARAGRAPH("Paragraphs");

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