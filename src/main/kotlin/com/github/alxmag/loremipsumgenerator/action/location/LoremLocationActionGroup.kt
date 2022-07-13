package com.github.alxmag.loremipsumgenerator.action.location

import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.action.base.SimpleLoremDialogAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class LoremLocationActionGroup : LoremPerformableActionGroupBase() {
    override val action: AnAction = SimpleLoremDialogAction("Generate Location Text") {
        emptyList()
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return emptyArray()
    }
}