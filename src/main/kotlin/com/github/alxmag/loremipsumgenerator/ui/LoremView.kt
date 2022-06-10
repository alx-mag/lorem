package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.model.LoremModel
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.ui.dsl.builder.*
import java.util.*
import javax.swing.JComboBox

abstract class LoremView<T : LoremModel>(protected val initialModel: T) {

    protected abstract val label: String
    protected abstract val availableUnits: List<TextAmountUnit>

    protected val propertyGraph = PropertyGraph()
    protected val amountProp =
        propertyGraph.property(initialModel.amount)
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

