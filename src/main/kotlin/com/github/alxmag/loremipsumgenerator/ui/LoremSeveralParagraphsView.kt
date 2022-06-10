package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.model.LoremSeveralParagraphsModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremSeveralParagraphsView(
    initialModel: LoremSeveralParagraphsModel,
    override val label: String,
    override val availableUnits: List<TextAmountUnit>
) : LoremView<LoremSeveralParagraphsModel>(initialModel) {

    override fun createModel(): LoremSeveralParagraphsModel {
        TODO("Not yet implemented")
    }
}

