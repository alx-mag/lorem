package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.ui.dsl.builder.*
import org.jetbrains.annotations.Nls
import java.util.*
import javax.swing.JComboBox

abstract class LoremView<T : LoremModel>(
    @Nls(capitalization = Nls.Capitalization.Sentence) private val label: String,
    initialModel: T,
    private val availableUnits: List<TextAmountUnit>
) {

    constructor(label: String, initialModel: T, vararg availableUnits: TextAmountUnit) : this(label, initialModel, availableUnits.toList())

    protected val propertyGraph = PropertyGraph()
    protected val amountProp = propertyGraph.property(initialModel.amount)
    protected val amountUnitProp = propertyGraph.property(initialModel.unit)
    protected val commentProp = propertyGraph.property("")

    protected var amountUnitCombo: JComboBox<TextAmountUnit>? = null

    fun createComponent() = panel {
        row {
            label("$label:").gap(RightGap.SMALL)
            spinner((1..1000), 1)
                .bindIntValue(getter = amountProp::get, setter = amountProp::set)
                .focused()
                .gap(RightGap.SMALL)
            when {
                availableUnits.size < 2 -> label(availableUnits.first().visibleName.replaceFirstChar {
                    it.lowercase(Locale.getDefault())
                })
                else -> {
                    amountUnitCombo = comboBox(availableUnits, TextAmountUnit.ListCellRenderer())
                        .bindItem(amountUnitProp)
                        .component
                }
            }
        }
        after()
    }


    open fun Panel.after() {

    }

    abstract fun createModel(): T
}

interface LoremModel {
    var amount: Int
    var unit: TextAmountUnit
}