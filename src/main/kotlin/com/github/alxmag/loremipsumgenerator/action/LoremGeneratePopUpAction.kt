package com.github.alxmag.loremipsumgenerator.action

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory

/**
 * Shows pop-up with placeholder text generation variants
 */
class LoremGeneratePopUpAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) = JBPopupFactory.getInstance().createActionGroupPopup(
        "Generate Placeholder Text",
        ActionManager.getInstance().getAction("lorem.GeneratePopupGroup") as ActionGroup,
        e.dataContext,
        true,
        true,
        true,
        null,
        10,
        null
    ).also {
        it.canClose()
    }
        .showInBestPositionFor(e.dataContext)
}