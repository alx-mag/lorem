package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremSentenceModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremSentenceView(
    initialModel: LoremSentenceModel,
    override val label: String = message("sentence.of"),
    override val availableUnits: List<TextAmountUnit> = listOf(TextAmountUnit.WORD)
) : LoremView<LoremSentenceModel>(initialModel) {

    override fun createModel(): LoremSentenceModel = LoremSentenceModel(
        amountProp.get(),
        amountUnitProp.get()
    )
}

