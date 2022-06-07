package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.layout.selectedValueMatches

class LoremParagraphView(model: LoremParagraphModel) : LoremView<LoremParagraphModel>(
    model,
    TextAmountUnit.CHARACTER,
    TextAmountUnit.WORD,
    TextAmountUnit.SENTENCE
) {

    override fun Panel.after() {
        row {
            comment(message("paragraph.will.contain.several.sentences"), 60)
                .visibleIf(amountUnitCombo.selectedValueMatches {
                    it == TextAmountUnit.CHARACTER || it == TextAmountUnit.WORD
                })
        }
    }

    override fun createModel(): LoremParagraphModel = LoremParagraphModel(
        amountProp.get(),
        amountUnitProp.get()
    )
}

// Default values are required for correct deserialization
class LoremParagraphModel(
    override var amount: Int = 5,
    override var unit: TextAmountUnit = TextAmountUnit.SENTENCE
) : LoremModel