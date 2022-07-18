package com.github.alxmag.loremipsumgenerator.action.recent

import com.github.alxmag.loremipsumgenerator.util.LoremConstants
import com.github.alxmag.loremipsumgenerator.util.removeDuplicates
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.components.*

@Service
@State(name = "RecentActions", storages = [Storage(LoremConstants.STORAGE_PATH)])
class LoremRecentActionsManager : SimplePersistentStateComponent<LoremRecentActionsManager.State>(State()) {

    fun registerAction(actionId: String) {
        state.actionHistory = buildList {
            add(actionId)
            addAll(state.actionHistory)
        }
            .removeDuplicates()
            .toMutableList()
    }

    fun getRecentActions(): List<AnAction> {
        val actionManager = ActionManager.getInstance()
        return state.actionHistory.mapNotNull(actionManager::getAction)
    }

    class State : BaseState() {
        var actionHistory by list<String>()
    }

    companion object {
        fun getInstance() = service<LoremRecentActionsManager>()
    }
}