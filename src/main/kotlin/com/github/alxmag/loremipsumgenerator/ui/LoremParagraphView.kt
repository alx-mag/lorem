package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel

class LoremParagraphView(initialModel: LoremParagraphModel) : LoremView<LoremParagraphModel>(initialModel) {

    override fun createComponent(): DialogPanel = panel {
        textAmountRow(
            message("paragraph.of"),
            initialModel::amount,
            initialModel::unit,
            listOf(TextAmountUnit.WORD, TextAmountUnit.SENTENCE),
            configureAmountSpinner = {},
            configureUnitCombo = { }
        )/*.layout(RowLayout.PARENT_GRID)*/
        advancedSettings {
            wordsPerSentenceRow(initialModel.wordsPerSentence)
        }
    }

    override fun createModel(): LoremParagraphModel = initialModel
}

