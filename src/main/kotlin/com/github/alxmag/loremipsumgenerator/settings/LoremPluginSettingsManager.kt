package com.github.alxmag.loremipsumgenerator.settings

import com.github.alxmag.loremipsumgenerator.util.LoremConstants
import com.intellij.openapi.components.*
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import java.util.Locale

@Service
@State(name = "PluginSettings", storages = [Storage(LoremConstants.STORAGE_PATH)])
class LoremPluginSettingsManager : SimplePersistentStateComponent<LoremPluginSettingsManager.State>(State()) {

    class State : BaseState() {
        var showWelcomeMessage by property(true)

        @Suppress("MemberVisibilityCanBePrivate") // Should be public to make this field serializable
        var localeLanguageTag by string(defaultLanguageTag)

        var locale: Locale
            get() = readLocale()
            set(value) = writeLocale(value)

        private fun readLocale() = kotlin.runCatching {
            Locale.forLanguageTag(localeLanguageTag)
        }.onFailure {
            thisLogger().warn("Could not deserialize language tag", it)
        }.getOrDefault(Locale.ENGLISH)

        private fun writeLocale(locale: Locale) {
            localeLanguageTag = locale.toLanguageTag()
        }
    }


    companion object {
        fun getInstance() = service<LoremPluginSettingsManager>()

        private val defaultLanguageTag = Locale.ENGLISH.toLanguageTag()

        fun showLocaleSettingsDialog(project: Project) = LoremLocaleSettingsDialog(project).show()
    }
}