package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.services.LoremSettings
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import org.jetbrains.annotations.Nls
import kotlin.reflect.KMutableProperty0

fun Row.minMaxComponents(
    range: IntRange,
    applyToMinSpinner: (Cell<JBIntSpinner>) -> Unit,
    applyToMaxSpinner: (Cell<JBIntSpinner>) -> Unit
) {
    spinner(range, 1).also(applyToMinSpinner)
        .gap(RightGap.SMALL)

    label("–").gap(RightGap.SMALL)

    spinner(range, 1).also(applyToMaxSpinner)
}

fun Panel.minMaxRow(
    @Nls(capitalization = Nls.Capitalization.Sentence) label: String,
    range: IntRange,
    minMax: MinMax
): Row = minMaxRow(
    label,
    range,
    { it.bindIntValue(minMax::min) },
    { it.bindIntValue(minMax::max) }
)

fun Panel.minMaxRow(
    @Nls(capitalization = Nls.Capitalization.Sentence) label: String,
    range: IntRange,
    applyToMinSpinner: (Cell<JBIntSpinner>) -> Unit,
    applyToMaxSpinner: (Cell<JBIntSpinner>) -> Unit
) = row(label) {
    minMaxComponents(range, applyToMinSpinner, applyToMaxSpinner)
}

fun Panel.wordsPerSentenceRow(minMax: MinMax) = minMaxRow(
    message("words.per.sentence.label"),
    LoremSettings.instance.wordsPerSentenceRange,
    minMax
)

fun Panel.sentencesPerParagraphRow(minMax: MinMax) = minMaxRow(
    message("sentences.per.paragraph.label"),
    LoremSettings.instance.sentencesPerParagraphRange,
    minMax
)

/**
 * Example: `Sentence of: 10 Words`
 */
inline fun Panel.textAmountRow(
    @Nls(capitalization = Nls.Capitalization.Sentence) label: String,
    amountProp: KMutableProperty0<Int>,
    unitProp: KMutableProperty0<TextAmountUnit>,
    availableUnits: List<TextAmountUnit>,
    crossinline configureAmountSpinner: (Cell<JBIntSpinner>) -> Unit,
    crossinline configureUnitCombo: (Cell<ComboBox<TextAmountUnit>>) -> Unit
) = row {
    label(label).gap(RightGap.SMALL)
    spinner(LoremSettings.instance.commonTextAmountRange, 1)
        .bindIntValue(amountProp)
        .focused()
        .gap(RightGap.SMALL)
        .also(configureAmountSpinner)
    when (availableUnits.size) {
        0 -> throw IllegalArgumentException("Amount units are empty")
        1 -> label(availableUnits.first().visibleNameLabel())
        else -> comboBox(availableUnits, TextAmountUnit.ListCellRenderer())
            .bindItem(unitProp.toNullableProperty())
            .also(configureUnitCombo)
    }
}

fun Panel.otherUnitsHintRow(amountProp: ObservableMutableProperty<String>, unit: TextAmountUnit) = row {
    cell()
    comment("").horizontalAlign(HorizontalAlign.RIGHT)
        .bindText(amountProp)
        .gap(RightGap.SMALL)
    comment(unit.visibleName)
}

fun Panel.advancedSettings(block: Panel.() -> Unit) = collapsibleGroup(message("advanced.settings.title")) {
    block()
}

