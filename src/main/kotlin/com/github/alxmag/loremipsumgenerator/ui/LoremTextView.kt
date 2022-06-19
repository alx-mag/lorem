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
import com.intellij.ui.layout.selectedValueMatches

class LoremTextView(initialModel: LoremTextModel) : LoremView<LoremTextModel>(initialModel) {

    private val graph = PropertyGraph()

    private val units = listOf(
        TextAmountUnit.PARAGRAPH,
        TextAmountUnit.SENTENCE,
    )

    private val amountUnits = listOf(
        TextAmountUnit.PARAGRAPH,
        TextAmountUnit.SENTENCE,
        TextAmountUnit.WORD
    )

    private val unitProp = graph.property(initialModel.unit)

    private lateinit var unitCombo: ComboBox<TextAmountUnit>
    private lateinit var amountSpinner: JBIntSpinner
    private lateinit var amountUnitCombo: ComboBox<TextAmountUnit>

    private val textAmountChangedCallbacks: MutableList<() -> Unit> = mutableListOf()

    override fun createComponent(): DialogPanel = panel {
        row {
            comboBox(units, simpleRenderer { it.visibleName(false) })
                .bindItem(unitProp)
                .smallRightGap()
                .applyToComponent {
                    unitCombo = this
                    addItemListener {
                        onUnitChanged()
                    }
                }
            @Suppress("DialogTitleCapitalization")
            label("of:")
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
                .bindItem(initialModel::ofUnit.toNullableProperty())
                .horizontalAlign(HorizontalAlign.FILL)
                .applyToComponent {
                    amountUnitCombo = this
                    addItemListener {
                        applyTextAmountChangeCallbacks()
                    }
                }
        }.layout(RowLayout.PARENT_GRID)

        createTextAmountHints()
    }.also {
        applyTextAmountChangeCallbacks()
    }

    private fun onUnitChanged() {
        amountUnitCombo.apply {
            // Compute amount units that can be used with selected unit
            val amountUnits = unitCombo.item
                ?.let(::getAmountUnits)
                ?.filter(amountUnits::contains)
                ?.sortedDescending()
                ?: return

            // Update combo box model
            removeAllItems()
            amountUnits.forEach(::addItem)

            // Disable comboBox if there is only single option
            isEnabled = itemCount > 1
        }
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
            val visibilityPredicate = amountUnitCombo.selectedValueMatches {
                val unit = unitCombo.item
                unit != null
                        && getAmountUnits(unit).contains(amountUnit)
                        && it != amountUnit
                        && unit != amountUnit
            }

            row {
                cell()
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

    private fun getAmountUnits(unit: TextAmountUnit) = unit.getAmountUnits()

    private fun getConverterData() = ConverterData.create(
        Triple(TextAmountUnit.PARAGRAPH, TextAmountUnit.SENTENCE, MinMax(5, 10)),
        Triple(TextAmountUnit.SENTENCE, TextAmountUnit.WORD, MinMax(5, 10))
    )

    private fun applyTextAmountChangeCallbacks() {
        textAmountChangedCallbacks.forEach { it() }
    }

    override fun createModel(): LoremTextModel = initialModel
}

