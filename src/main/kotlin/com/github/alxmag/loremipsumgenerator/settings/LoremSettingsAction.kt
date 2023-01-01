package com.github.alxmag.loremipsumgenerator.settings

import com.intellij.codeInsight.hint.HintManagerImpl
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class LoremSettingsAction : AnAction(), HintManagerImpl.ActionToIgnore {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        LoremPluginSettingsManager.showLocaleSettingsDialog(project)
    }

    companion object {
        const val ID = "lorem.locale.settings"
    }
}