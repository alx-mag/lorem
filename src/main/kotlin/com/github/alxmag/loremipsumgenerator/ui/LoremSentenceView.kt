package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremSentenceModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class LoremSentenceView(initialModel: LoremSentenceModel) : LoremView<LoremSentenceModel>(initialModel) {

    override fun createComponent(): JComponent = panel {
        textAmountRow(
            message("sentence.of"),
            initialModel::amount,
            initialModel::unit,
            listOf(TextAmountUnit.WORD),
            configureAmountSpinner = { },
            configureUnitCombo = { }
        )
    }

    override fun createModel(): LoremSentenceModel = initialModel
}

