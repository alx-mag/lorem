package com.github.alxmag.loremipsumgenerator.action.recent

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.actionSystem.ex.AnActionListener

class LoremActionsListener : AnActionListener {

    override fun afterActionPerformed(action: AnAction, event: AnActionEvent, result: AnActionResult) {
        if (action !is LoremMemorizeAction) return
        val id = ActionManager.getInstance().getId(action)
        if (id != null) {
            LoremRecentActionsManager.getInstance().registerAction(id)
        }
    }
}