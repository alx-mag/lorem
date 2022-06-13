package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.model.LoremSeveralParagraphsModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.ui.dsl.builder.Panel

class LoremSeveralParagraphsView(
    initialModel: LoremSeveralParagraphsModel,
    override val label: String,
    override val availableUnits: List<TextAmountUnit>
) : LoremView<LoremSeveralParagraphsModel>(initialModel) {

    override fun createModel(): LoremSeveralParagraphsModel = initialModel

    override fun setUpSettingsUi(panel: Panel) {
        with(panel) {
            wordsPerSentenceRow(initialModel.wordsPerSentence)
            sentencesPerParagraphRow(initialModel.sentencesPerParagraph)
        }
    }
}