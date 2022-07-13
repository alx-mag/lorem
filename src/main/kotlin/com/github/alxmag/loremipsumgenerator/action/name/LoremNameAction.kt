package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.action.base.SimpleLoremDialogAction
import com.github.alxmag.loremipsumgenerator.action.base.SimpleLoremGenerateTextAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class LoremNameActionGroup : LoremPerformableActionGroupBase() {

    override val action: AnAction = SimpleLoremDialogAction("Generate Name") {
        LoremNameTemplatesManager.getInstance(it.project).getNameTemplates()
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val project = e?.project ?: return emptyArray()
        return LoremNameTemplatesManager.getInstance(project)
            .getNameTemplates()
            .map(SimpleLoremGenerateTextAction.Companion::fromTemplate)
            .toTypedArray()
    }
}