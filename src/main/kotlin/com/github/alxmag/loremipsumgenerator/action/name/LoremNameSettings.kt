package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.util.LoremConstants
import com.intellij.openapi.components.*

@State(name = "LoremNameSettings", storages = [Storage(LoremConstants.STORAGE_PATH)])
@Service
class LoremNameSettings : SimplePersistentStateComponent<LoremNameSettings.State>(State()) {

    fun applyModelToHistory(nameModel: LoremNameModel) {
        state.preselectedNameModel = nameModel
        // TODO add into history
    }

    class State : BaseState() {
        var preselectedNameModel: LoremNameModel by property(LoremNameModel()) { it == LoremNameModel() }
        var nameModelHistory by list<LoremNameModel>()
    }

    companion object {
        fun getInstance() = service<LoremNameSettings>()
    }
}