package com.github.alxmag.loremipsumgenerator.action.recent

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup

class LoremRecentActionsGroup : DefaultActionGroup() {
    override fun getChildren(e: AnActionEvent?) = LoremRecentActionsManager.getInstance()
        .getRecentActions()
        .toTypedArray()
}