package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.services.LoremSettings
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.intellij.openapi.observable.properties.GraphProperty
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.*
import org.jetbrains.annotations.Nls

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

fun <T : JBIntSpinner> Cell<T>.bindIntProperty(prop: GraphProperty<Int>) = bindIntValue(prop::get, prop::set)

fun Panel.advancedSettings(block: Panel.() -> Unit) = collapsibleGroup(message("advanced.settings.title")) {
    block()
}

