package com.github.alxmag.loremipsumgenerator.util

import com.intellij.ui.ColoredListCellRenderer
import javax.swing.JList
import javax.swing.ListCellRenderer

object ListCellRendererFactory {
    fun <T> simpleRenderer(toString: (T) -> String): ListCellRenderer<T?> = object : ColoredListCellRenderer<T?>() {
        override fun customizeCellRenderer(
            list: JList<out T>,
            value: T?,
            index: Int,
            selected: Boolean,
            hasFocus: Boolean
        ) {
            value ?: return
            append(toString(value))
        }
    }
}