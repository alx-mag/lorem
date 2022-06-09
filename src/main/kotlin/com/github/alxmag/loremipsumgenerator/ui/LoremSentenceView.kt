package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremSentenceView(model: LoremSentenceModel) : LoremView<LoremSentenceModel>(
    message("sentence.of"),
    model,
//    TextAmountUnit.CHARACTER,
    TextAmountUnit.WORD
) {

    override fun createModel(): LoremSentenceModel = LoremSentenceModel(
        amountProp.get(),
        amountUnitProp.get()
    )
}

class LoremSentenceModel(
    override var amount: Int = 5,
    override var unit: TextAmountUnit = TextAmountUnit.WORD
) : LoremModel