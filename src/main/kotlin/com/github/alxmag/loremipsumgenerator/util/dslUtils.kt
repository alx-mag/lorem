package com.github.alxmag.loremipsumgenerator.util

import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.RightGap
import javax.swing.JComponent

fun <T : JComponent> Cell<T>.smallRightGap() = gap(RightGap.SMALL)