package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremEditorBalloonManager
import com.github.alxmag.loremipsumgenerator.action.recent.LoremRecentActionsManager
import com.github.alxmag.loremipsumgenerator.util.LoremActionPlace
import com.intellij.codeInsight.hint.HintManagerImpl
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.IdeFocusManager

/**
 * Shows pop-up with placeholder text generation variants
 */
class LoremGeneratePopUpAction : AnAction(), HintManagerImpl.ActionToIgnore {

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.getData(CommonDataKeys.EDITOR) != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        // Repeat last action if editor balloon is showing
        val editorBalloonManager = LoremEditorBalloonManager.getInstance(project)
        if (editorBalloonManager.isShowing() && editorBalloonManager.repeatAction()) {
            return
        }

        LoremPopupManager.getInstance(project).performPopUpAction(e)
    }

    companion object {
        const val ID = "lorem.GeneratePopupAction"
    }
}

@Service(Service.Level.PROJECT)
class LoremPopupManager {

    private var isShowing: Boolean = false

    fun performPopUpAction(e: AnActionEvent) {
        if (isShowing) {
            println("Showing")
            return
        }

        val actionManager = ActionManager.getInstance()
        val popup = JBPopupFactory.getInstance().createActionGroupPopup(
            message("main.popup.title"),
            actionManager.getAction("lorem.GeneratePopupGroup") as ActionGroup,
            e.dataContext,
            JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING,
            true,
            null,
            30,
            null,
            LoremActionPlace.EDITOR_POPUP
        )
        popup.showInBestPositionFor(e.dataContext)
    }

    companion object {
        fun getInstance(project: Project) = project.service<LoremPopupManager>()
    }
}

class LoremRecentActionsPopupAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        IdeFocusManager.getInstance(project).doWhenFocusSettlesDown {
            JBPopupFactory.getInstance().createActionGroupPopup(
                message("lorem.recent.actions"),
                DefaultActionGroup(LoremRecentActionsManager.getInstance().getRecentActions()),
                e.dataContext,
                JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING,
                true,
                null,
                30,
                null,
                LoremActionPlace.EDITOR_POPUP
            ).showInBestPositionFor(e.dataContext)
        }
    }
}