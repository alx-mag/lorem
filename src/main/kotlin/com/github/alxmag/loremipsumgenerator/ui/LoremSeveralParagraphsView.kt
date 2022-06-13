package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle
import com.github.alxmag.loremipsumgenerator.model.LoremSeveralParagraphsModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class LoremSeveralParagraphsView(initialModel: LoremSeveralParagraphsModel) :
    LoremView<LoremSeveralParagraphsModel>(initialModel) {

    override fun createComponent(): JComponent = panel {
        textAmountRow(
            MyBundle.message("text.of"),
            initialModel::amount,
            initialModel::unit,
            listOf(TextAmountUnit.WORD, TextAmountUnit.SENTENCE, TextAmountUnit.PARAGRAPH),
            configureAmountSpinner = {},
            configureUnitCombo = { }
        )
        advancedSettings {
            wordsPerSentenceRow(initialModel.wordsPerSentence)
            sentencesPerParagraphRow(initialModel.sentencesPerParagraph)
        }
    }

    override fun createModel(): LoremSeveralParagraphsModel = initialModel

}