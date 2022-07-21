package com.github.alxmag.loremipsumgenerator.service

import com.github.alxmag.loremipsumgenerator.util.LoremConstants
import com.intellij.openapi.components.*

@Service
@State(name = "PluginSettings", storages = [Storage(LoremConstants.STORAGE_PATH)])
class LoremPluginSettingsManager : SimplePersistentStateComponent<LoremPluginSettingsManager.State>(State()) {

    class State : BaseState() {
        var showWelcomeMessage by property(true)
    }

    companion object {
        fun getInstance() = service<LoremPluginSettingsManager>()
    }
}