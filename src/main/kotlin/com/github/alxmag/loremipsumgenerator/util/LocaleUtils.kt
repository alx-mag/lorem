package com.github.alxmag.loremipsumgenerator.util

import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManager
import com.jetbrains.rd.util.use
import java.io.InputStreamReader
import java.io.Reader
import java.util.*

object LocaleUtils {

    val dataFakerLocales by lazy(::readDataFakerLocales)

    private fun readDataFakerLocales(): Set<Locale> {
        val text = getFileReader("/locales/dataFakerLocales.txt")
            ?.use { read -> read.readText() }
            ?: return emptySet()

        return text.split(",")
            .asSequence()
            .map { it.trim() }
            .map(Locale::forLanguageTag)
            .toSet()
    }

    @Suppress("SameParameterValue")
    private fun getFileReader(filepath: String): Reader? = LocaleUtils::class.java.getResourceAsStream(filepath)
        ?.let(::InputStreamReader)

    fun getLocaleDisplayName(locale: Locale, displayLocale: Locale = getIdeLocale()) = buildString {
        val displayLanguage = locale.getDisplayLanguage(displayLocale).capitalize(displayLocale)
        append(displayLanguage)

        val extras = with(locale) {
            sequenceOf(
                getDisplayScript(displayLocale),
                getDisplayCountry(displayLocale),
                getDisplayVariant(displayLocale)
            )
        }.filter {
            it.isNotBlank()
        }.map {
            it.capitalize(displayLocale)
        }.toList()

        if (extras.isNotEmpty()) {
            append(" ")
            append("(")
            append(extras.joinToString(", "))
            append(")")
        }
    }

    fun getIdeLocale(): Locale {
        val loadedPlugins: List<IdeaPluginDescriptor> = PluginManager.getLoadedPlugins()
        return when {
            loadedPlugins.any { it.pluginId.idString == "com.intellij.zh" } -> Locale.SIMPLIFIED_CHINESE
            loadedPlugins.any { it.pluginId.idString == "com.intellij.ko" } -> Locale.KOREAN
            loadedPlugins.any { it.pluginId.idString == "com.intellij.ja" } -> Locale.JAPANESE
            else -> Locale.ENGLISH
        }
    }

    private fun String.capitalize(locale: Locale) = this.replaceFirstChar { firstChar ->
        firstChar.titlecase(locale)
    }
}