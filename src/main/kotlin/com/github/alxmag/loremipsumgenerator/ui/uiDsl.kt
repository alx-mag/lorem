package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.*
import org.jetbrains.annotations.Nls

fun Row.minMaxComponents(
    range: IntRange,
    applyToMinSpinner: (Cell<JBIntSpinner>) -> Unit,
    applyToMaxSpinner: (Cell<JBIntSpinner>) -> Unit
) {
    spinner(range, 1)
        .also(applyToMinSpinner)
        .gap(RightGap.SMALL)


    label("–").gap(RightGap.SMALL)

    spinner(range, 1)
        .also(applyToMaxSpinner)
}

fun Panel.minMaxRow(
    @Nls(capitalization = Nls.Capitalization.Sentence) label: String,
    range: IntRange,
    minMax: MinMax,
    configureMinSpinner: (Cell<JBIntSpinner>) -> Unit = { },
    configureMaxSpinner: (Cell<JBIntSpinner>) -> Unit = { }
): Row = minMaxRow(
    label,
    range,
    {
        it.bindIntValue(minMax::min)
        configureMinSpinner(it)
    },
    {
        it.bindIntValue(minMax::max)
        configureMaxSpinner(it)
    }
)

fun Panel.minMaxRow(
    @Nls(capitalization = Nls.Capitalization.Sentence) label: String,
    range: IntRange,
    applyToMinSpinner: (Cell<JBIntSpinner>) -> Unit,
    applyToMaxSpinner: (Cell<JBIntSpinner>) -> Unit
) = row(label) {
    minMaxComponents(range, applyToMinSpinner, applyToMaxSpinner)
}
