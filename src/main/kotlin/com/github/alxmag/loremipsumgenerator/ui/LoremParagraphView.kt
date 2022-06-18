package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.util.ConverterData
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.TextAmountConverter
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RowLayout
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.layout.selectedValueMatches

class LoremParagraphView(initialModel: LoremParagraphModel) : LoremView<LoremParagraphModel>(initialModel) {

    private val graph = PropertyGraph()

    private val textAmountUnits = listOf(
        TextAmountUnit.WORD,
        TextAmountUnit.SENTENCE
    )

    private lateinit var amountSpinner: JBIntSpinner
    private lateinit var unitCombo: ComboBox<TextAmountUnit>

    private val textAmountChangedCallbacks: MutableList<() -> Unit> = mutableListOf()

    override fun createComponent(): DialogPanel = panel {
        textAmountRow(
            message("paragraph.of"),
            initialModel::amount,
            initialModel::unit,
            textAmountUnits,
            configureAmountSpinner = {
                amountSpinner = it.component
                amountSpinner.addChangeListener {
                    applyTextAmountChangeCallbacks()
                }
            },
            configureUnitCombo = {
                unitCombo = it.component
                unitCombo.addItemListener {
                    applyTextAmountChangeCallbacks()
                }
            }
        ).layout(RowLayout.PARENT_GRID)
        createTextAmountHints()
    }.also {
        applyTextAmountChangeCallbacks()
    }

    private fun Panel.createTextAmountHints() {
        for (unit in textAmountUnits) {
            val amountProp = graph.property("")
            textAmountChangedCallbacks.add {
                val newAmount = TextAmountConverter(getConverterData()).convert(
                    MinMax(amountSpinner.number),
                    unitCombo.item,
                    unit
                )
                amountProp.set(newAmount.toPresentableString())
            }
            otherUnitsHintRow(amountProp, unit)
                .visibleIf(unitCombo.selectedValueMatches { it != unit })
                .layout(RowLayout.PARENT_GRID)
        }
    }

    private fun getConverterData() = ConverterData.create(
        Triple(TextAmountUnit.PARAGRAPH, TextAmountUnit.SENTENCE, MinMax(5, 10)),
        Triple(TextAmountUnit.SENTENCE, TextAmountUnit.WORD, MinMax(5, 10))
    )

    private fun applyTextAmountChangeCallbacks() {
        textAmountChangedCallbacks.forEach { it() }
    }

    override fun createModel(): LoremParagraphModel = initialModel
}

