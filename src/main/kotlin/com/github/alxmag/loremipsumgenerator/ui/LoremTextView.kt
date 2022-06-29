package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.model.LoremTextModel
import com.github.alxmag.loremipsumgenerator.services.LoremSettings
import com.github.alxmag.loremipsumgenerator.util.*
import com.github.alxmag.loremipsumgenerator.util.ListCellRendererFactory.simpleRenderer
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalGaps
import com.intellij.ui.layout.selectedValueMatches

class LoremTextView(initialModel: LoremTextModel) : LoremView<LoremTextModel>(initialModel) {

    private val graph = PropertyGraph()

    private val amountUnits = listOf(
        TextAmountUnit.SENTENCE,
        TextAmountUnit.WORD
    )

    private lateinit var amountSpinner: JBIntSpinner
    private lateinit var amountUnitCombo: ComboBox<TextAmountUnit>

    private val textAmountChangedCallbacks: MutableList<() -> Unit> = mutableListOf()

    override fun createComponent(): DialogPanel = panel {
        row {
            label("Text of:")
            spinner(LoremSettings.instance.commonTextAmountRange, 1)
                .bindIntValue(initialModel::amount)
                .focused()
                .gap(RightGap.SMALL)
                .applyToComponent {
                    amountSpinner = this
                    addChangeListener {
                        applyTextAmountChangeCallbacks()
                    }
                }
            comboBox(amountUnits, simpleRenderer { it.visibleName(true) })
                .bindItem(initialModel::unit.toNullableProperty())
                .applyToComponent {
                    amountUnitCombo = this
                    addItemListener {
                        applyTextAmountChangeCallbacks()
                    }
                }
        }.layout(RowLayout.PARENT_GRID)

        separator().customize(VerticalGaps.EMPTY)

        createTextAmountHints()
    }.also {
        applyTextAmountChangeCallbacks()
    }


    private fun Panel.createTextAmountHints() {
        for (amountUnit in amountUnits) {
            val amountProp = graph.property("")
            val unitProp = graph.property("")

            textAmountChangedCallbacks.add {
                val unit = amountUnitCombo.item ?: return@add

                // Compute amount
                val amount = TextAmountConverter(getConverterData()).convert(
                    MinMax(amountSpinner.number),
                    unit,
                    amountUnit
                )
                amountProp.set(amount.toPresentableString())

                // Compute amount unit visible name (should it ends with 's')
                val unitValue = when {
                    amount.isRange() || amount.min > 1 -> amountUnit.visibleName(true)
                    else -> amountUnit.visibleName(false)
                }
                unitProp.set(unitValue)
            }

            // Show the row if it represents any of amount unit of selected unit
            val visibilityPredicate = amountUnitCombo.selectedValueMatches { parentAmount ->
                val visibleSubUnits = parentAmount?.let(::getSubUnits) ?: emptyList()
                visibleSubUnits.contains(amountUnit)
            }

            row {
                cell()
                comment("").horizontalAlign(HorizontalAlign.RIGHT)
                    .bindText(amountProp)
                    .gap(RightGap.SMALL)
                comment(amountUnit.visibleName(true))
                    .bindText(unitProp)
            }
                .visibleIf(visibilityPredicate)
                .layout(RowLayout.PARENT_GRID)
        }
    }

    private fun getSubUnits(unit: TextAmountUnit) = unit.getSubUnits()

    private fun getConverterData() = ConverterData.create(
        Triple(TextAmountUnit.PARAGRAPH, TextAmountUnit.SENTENCE, MinMax(5, 10)),
        Triple(TextAmountUnit.SENTENCE, TextAmountUnit.WORD, MinMax(5, 10))
    )

    private fun applyTextAmountChangeCallbacks() {
        textAmountChangedCallbacks.forEach { it() }
    }

    override fun createModel(): LoremTextModel = initialModel
}

