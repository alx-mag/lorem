package com.github.alxmag.loremipsumgenerator.settings

import com.github.alxmag.loremipsumgenerator.util.LocaleUtils
import com.github.alxmag.loremipsumgenerator.util.ui.DoubleDeckListCellRenderer
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.ComboboxSpeedSearch
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.toNullableProperty
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import java.util.*
import javax.swing.JComponent
import javax.swing.JList

class LoremLocaleSettingsDialog(project: Project) : DialogWrapper(project) {

    init {
        title = "Lorem Locale"
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row {
            val localeCellRenderer = LocaleListCellRenderer()
            comboBox(LocaleUtils.dataFakerLocales, localeCellRenderer)
                .bindItem(LoremPluginSettingsManager.getInstance().state::locale.toNullableProperty())
                .horizontalAlign(HorizontalAlign.FILL)
                .focused()
                .applyToComponent {
                    ComboboxSpeedSearch.installSpeedSearch(this, localeCellRenderer::getMainText)
                }
        }
        row {
            comment("Locale affects geography-specific values such as regions, phone numbers, emails etc.")
        }
        row {
            comment("Please note that not all actions have a localized version.")
        }
    }
}

class LocaleListCellRenderer : DoubleDeckListCellRenderer<Locale?>() {

    /**
     * Displays locale name using language selected in IDE.
     */
    override val top: ColoredListCellRenderer<Locale?> = object : ColoredListCellRenderer<Locale?>() {
        override fun customizeCellRenderer(
            list: JList<out Locale>,
            value: Locale?,
            index: Int,
            selected: Boolean,
            hasFocus: Boolean
        ) {
            value ?: return
            append(getMainText(value), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
        }

    }

    /**
     * Displays locale name using its language.
     */
    override val bottom: ColoredListCellRenderer<Locale?> = object : ColoredListCellRenderer<Locale?>() {
        override fun customizeCellRenderer(
            list: JList<out Locale>,
            value: Locale?,
            index: Int,
            selected: Boolean,
            hasFocus: Boolean
        ) {
            value ?: return
            append(LocaleUtils.getLocaleDisplayName(value, value), SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }

    override fun getMainText(value: Locale?): String {
        value ?: return ""
        return LocaleUtils.getLocaleDisplayName(value, LocaleUtils.getIdeLocale())
    }
}