package com.github.alxmag.loremipsumgenerator.action.placeholdertext.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextModel
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextSettings
import com.github.alxmag.loremipsumgenerator.util.*
import com.github.alxmag.loremipsumgenerator.util.ListCellRendererFactory.simpleRenderer
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalGaps
import com.intellij.ui.layout.selectedValueMatches
import org.intellij.lang.annotations.Language
import javax.swing.event.HyperlinkEvent

class LoremPlaceholderTextView(private val project: Project?, initialModel: LoremPlaceholderTextModel) {

    private val myUnitChangedListeners = mutableListOf<UnitChangedListener>()
    private val vm = ViewModel(initialModel)

    private val amountUnits = listOf(
        TextAmountUnit.SENTENCE,
        TextAmountUnit.WORD
    )

    private lateinit var amountSpinner: JBIntSpinner
    private lateinit var amountUnitCombo: ComboBox<TextAmountUnit>

    private val amountHintRefreshers: MutableList<() -> Unit> = mutableListOf()

    val panel by lazy(::createPanel)

    private fun createPanel(): DialogPanel = panel {
        row {
            spinner((1..1_000_000), 1)
                .bindIntValue(vm.amount::get, vm.amount::set)
                .focused()
                .gap(RightGap.SMALL)
                .applyToComponent {
                    amountSpinner = this
                    addChangeListener {
                        refreshAmountHints()
                    }
                }
            comboBox(amountUnits, simpleRenderer { it.visibleName(true) })
                .bindItem(vm.unit)
                .applyToComponent {
                    amountUnitCombo = this
                    addItemListener {
                        refreshAmountHints()
                    }
                }
        }.layout(RowLayout.PARENT_GRID)


        separator().customize(VerticalGaps.EMPTY)

        createTextAmountHints()
    }.also {
        refreshAmountHints()
    }

    private fun Panel.createTextAmountHints() {
        for (hintUnit in amountUnits) {
            val amountProp = vm.property("")
            val unitProp = vm.property("")

            amountHintRefreshers.add {
                val unit = amountUnitCombo.item ?: return@add

                // Compute amount
                val amount = TextAmountConverter(getConverterData()).convert(
                    MinMax(amountSpinner.number),
                    unit,
                    hintUnit
                )

                @Language("HTML")
                val value = "<a href='${hintUnit.name}'>${amount.toPresentableString()}</a>"
                amountProp.set(value)

                // Compute amount unit visible name (should it ends with 's')
                val unitValue = when {
                    amount.isRange() || amount.min > 1 -> hintUnit.visibleName(true)
                    else -> hintUnit.visibleName(false)
                }
                unitProp.set(unitValue)
            }

            // Show the row if it represents any of amount unit of selected unit
            val visibilityPredicate = amountUnitCombo.selectedValueMatches { parentAmount ->
                val visibleSubUnits = parentAmount?.let(::getSubUnits) ?: emptyList()
                visibleSubUnits.contains(hintUnit)
            }

            row {
                comment("", action = ::onAmountHintClick)
                    .horizontalAlign(HorizontalAlign.RIGHT)
                    .bindText(amountProp)
                    .gap(RightGap.SMALL)
                    .applyToComponent {
                        toolTipText = message("click.to.set.up.amount", hintUnit.visibleName(true).lowercase())
                    }
                comment(hintUnit.visibleName(true))
                    .bindText(unitProp)
            }
                .visibleIf(visibilityPredicate)
                .layout(RowLayout.PARENT_GRID)
        }
    }

    private fun getSubUnits(unit: TextAmountUnit) = unit.getSubUnits()

    // TODO put to amountHints settings
    private fun getConverterData() = ConverterData.create(
        Triple(TextAmountUnit.PARAGRAPH, TextAmountUnit.SENTENCE, MinMax(5, 10)),
        Triple(TextAmountUnit.SENTENCE, TextAmountUnit.WORD, LoremPlaceholderTextSettings.getInstance().wordsPerSentence)
    )

    private fun refreshAmountHints() = amountHintRefreshers.forEach { it() }

    fun createModel(): LoremPlaceholderTextModel {
        panel.apply()
        return LoremPlaceholderTextModel(
            vm.unit.get(),
            vm.amount.get()
        )
    }

    fun addUnitChangedListener(listener: UnitChangedListener) = myUnitChangedListeners.add(listener)

    fun fireUnitChanged() = myUnitChangedListeners.forEach {
        it()
    }

    private fun onAmountHintClick(event: HyperlinkEvent) {
        // Get amount unit from URL
        val amountUnit = kotlin.runCatching {
            event.description?.let { TextAmountUnit.valueOf(it) }
        }.getOrNull()

        when (amountUnit) {
            TextAmountUnit.WORD -> {
                // TODO put to amountHints settings
                val wordsSettingsModel = WordsSettingsDialog.show(project)
                if (wordsSettingsModel != null) {
                    refreshAmountHints()
                }
            }

            else -> {
                // Ignore
            }
        }
    }

    inner class ViewModel(textModel: LoremPlaceholderTextModel) : AbstractViewModel() {
        val amount = property(textModel.amount)
        val unit = property(textModel.unit)

        init {
            unit.afterChange { fireUnitChanged() }
        }
    }

    fun interface UnitChangedListener {
        operator fun invoke()
    }
}


