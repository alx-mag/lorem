package com.github.alxmag.loremipsumgenerator.settings

import com.github.alxmag.loremipsumgenerator.MyBundle.message
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
        title = message("lorem.localization.settings")
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row {
            val localeCellRenderer = LocaleListCellRenderer()
            val items = LocaleUtils.dataFakerLocales
                .sortedBy(LocaleListCellRenderer::getDisplayText)
            comboBox(items, localeCellRenderer)
                .bindItem(LoremPluginSettingsManager.getInstance().state::locale.toNullableProperty())
                .horizontalAlign(HorizontalAlign.FILL)
                .focused()
                .comment(message("lorem.localization.settings.hint"))
                .applyToComponent {
                    ComboboxSpeedSearch.installSpeedSearch(this, LocaleListCellRenderer.Companion::getDisplayText)
                }
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
            append(getDisplayText(value), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
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
            append(getDisplayText(value, value), SimpleTextAttributes.GRAYED_ATTRIBUTES)
        }
    }


    companion object {
        fun getDisplayText(locale: Locale, inLocale: Locale = LocaleUtils.getIdeLocale()): String {
            return LocaleUtils.getLocaleDisplayName(locale.normalize(), inLocale.normalize())
        }

        /**
         * Some locales have non-standard language tag, which causes them to be displayed incorrectly.
         * This function converts locale into the standard format.
         */
        private fun Locale.normalize(): Locale {
            return when (this.toLanguageTag()) {
                "by" -> Locale.forLanguageTag("be-BY")
                "en-PAK" -> Locale.forLanguageTag("en-PK")
                "pak" -> Locale.forLanguageTag("en-PK")
                else -> this
            }
        }
    }
}