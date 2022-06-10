package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RightGap
import com.intellij.ui.dsl.builder.bindIntValue

class LoremParagraphView(
    initialModel: LoremParagraphModel,
    override val label: String = message("paragraph.of"),
    override val availableUnits: List<TextAmountUnit> = listOf(
        TextAmountUnit.WORD,
        TextAmountUnit.SENTENCE
    )
) : LoremView<LoremParagraphModel>(initialModel) {

    private val wordsPerSentenceMinProp = propertyGraph.property(initialModel.wordsPerSentence.min)
    private val wordsPerSentenceMaxProp = propertyGraph.property(initialModel.wordsPerSentence.max)

    private lateinit var wordsPerSentenceMinComponent: JBIntSpinner
    private lateinit var wordsPerSentenceMaxComponent: JBIntSpinner

    override fun Panel.after() {
        collapsibleGroup(message("advanced.settings.title")) {
            row("Words per sentence:") {
                wordsPerSentenceMinComponent = spinner((1..50), 1).gap(RightGap.SMALL)
                    .bindIntValue(wordsPerSentenceMinProp::get, wordsPerSentenceMinProp::set)
                    .component

                @Suppress("DialogTitleCapitalization")
                label("to").gap(RightGap.SMALL)

                wordsPerSentenceMaxComponent = spinner((1..50), 1)
                    .bindIntValue(wordsPerSentenceMaxProp::get, wordsPerSentenceMaxProp::set)
                    .component
            }
        }
    }

    override fun createModel(): LoremParagraphModel = LoremParagraphModel(
        amountProp.get(),
        amountUnitProp.get(),
        MinMax(wordsPerSentenceMinProp.get(), wordsPerSentenceMaxProp.get())
    )
}

