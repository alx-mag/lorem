package com.github.alxmag.loremipsumgenerator.util.ui

import com.intellij.ui.ColoredListCellRenderer
import com.intellij.util.ui.JBUI
import java.awt.Component
import javax.swing.JList
import javax.swing.ListCellRenderer

abstract class DoubleDeckListCellRenderer<T> : ListCellRenderer<T> {
    protected abstract val top: ColoredListCellRenderer<T>
    protected abstract val bottom: ColoredListCellRenderer<T>

    override fun getListCellRendererComponent(
        list: JList<out T>?,
        value: T,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        return JBUI.Panels.simplePanel()
            .addToTop(top.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus))
            .addToBottom(bottom.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus))
    }
}