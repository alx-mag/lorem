package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.util.LoremActionPlace
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory

/**
 * Shows pop-up with placeholder text generation variants
 */
class LoremGeneratePopUpAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) = JBPopupFactory.getInstance()
        .createActionGroupPopup(
            message("main.popup.title"),
            ActionManager.getInstance().getAction("lorem.GeneratePopupGroup") as ActionGroup,
            e.dataContext,
            JBPopupFactory.ActionSelectionAid.NUMBERING,
            true,
            null,
            10,
            null,
            LoremActionPlace.EDITOR_POPUP
        )
        .also {
            it.canClose()
        }
        .showInBestPositionFor(e.dataContext)
}