package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.bindIntValue
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComboBox

abstract class LoremView<T : LoremModel>(
    protected val initialModel: T,
    private val availableUnits: List<TextAmountUnit>
) {

    constructor(initialModel: T, vararg availableUnits: TextAmountUnit) : this(initialModel, availableUnits.toList())

    protected val propertyGraph = PropertyGraph()
    protected val amountProp = propertyGraph.property(initialModel.amount)
    protected val amountUnitProp = propertyGraph.property(initialModel.unit)
    protected val commentProp = propertyGraph.property("")

    lateinit var amountUnitCombo: JComboBox<TextAmountUnit>

    fun createComponent() = panel {
        row {
            label("Paragraph of")
            spinner((1..1000), 1)
                .bindIntValue(getter = amountProp::get, setter = amountProp::set)
                .focused()
            amountUnitCombo = comboBox(availableUnits, TextAmountUnit.ListCellRenderer())
                .bindItem(amountUnitProp)
                .component
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