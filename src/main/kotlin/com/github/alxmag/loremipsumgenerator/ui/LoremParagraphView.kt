package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RowLayout
import com.intellij.ui.dsl.builder.panel
import javax.swing.event.ChangeListener

class LoremParagraphView(initialModel: LoremParagraphModel) : LoremView<LoremParagraphModel>(initialModel) {

    private val graph = PropertyGraph()

    private val textAmountUnits = listOf(TextAmountUnit.WORD, TextAmountUnit.SENTENCE)

    private lateinit var amountSpinner: JBIntSpinner
    private lateinit var unitCombo: ComboBox<TextAmountUnit>

    override fun createComponent(): DialogPanel = panel {
        textAmountRow(
            message("paragraph.of"),
            initialModel::amount,
            initialModel::unit,
            textAmountUnits,
            configureAmountSpinner = {
                amountSpinner = it.component
            },
            configureUnitCombo = {
                unitCombo = it.component
            }
        ).layout(RowLayout.PARENT_GRID)
//        otherUnitsHintRow()

        advancedSettings {
            wordsPerSentenceRow(initialModel.wordsPerSentence)
        }
    }

    private val amountChangeListener = ChangeListener {

    }

    private fun Panel.createHintRow() {
        for (unit in textAmountUnits) {
            val amount = amountSpinner.number
            val item = unitCombo.item

            val amountProp = graph.property(0)

            otherUnitsHintRow(amountProp, unit)
        }

        textAmountUnits.forEach {

        }
    }


    override fun createModel(): LoremParagraphModel = initialModel
}

