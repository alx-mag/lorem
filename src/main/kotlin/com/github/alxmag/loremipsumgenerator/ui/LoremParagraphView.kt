package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.ui.dsl.builder.Panel

class LoremParagraphView(
    initialModel: LoremParagraphModel,
    override val label: String = message("paragraph.of"),
    override val availableUnits: List<TextAmountUnit> = listOf(
        TextAmountUnit.WORD,
        TextAmountUnit.SENTENCE
    )
) : LoremView<LoremParagraphModel>(initialModel) {

    override fun setUpSettingsUi(panel: Panel) {
        with(panel) {
            advancedSettings {
                wordsPerSentenceRow(initialModel.wordsPerSentence)
            }
        }
    }

    override fun createModel(): LoremParagraphModel = initialModel
}

