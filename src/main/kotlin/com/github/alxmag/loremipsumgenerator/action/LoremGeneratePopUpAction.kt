package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremEditorBalloonManager
import com.github.alxmag.loremipsumgenerator.action.recent.LoremRecentActionsManager
import com.github.alxmag.loremipsumgenerator.util.LoremActionPlace
import com.intellij.codeInsight.hint.HintManagerImpl
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.keymap.KeymapUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.IdeFocusManager
import javax.swing.SwingConstants

/**
 * Shows pop-up with placeholder text generation variants
 */
class LoremGeneratePopUpAction : AnAction(), HintManagerImpl.ActionToIgnore {

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.getData(CommonDataKeys.EDITOR) != null
    }

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

@Service
class LoremPopupManager(private val project: Project) {

    private var isShowing: Boolean = false

    fun performPopUpAction(e: AnActionEvent) {
        if (isShowing) {
            println("Showing")
            return
        }

        val popup = JBPopupFactory.getInstance().createActionGroupPopup(
            message("main.popup.title"),
            ActionManager.getInstance().getAction("lorem.GeneratePopupGroup") as ActionGroup,
            e.dataContext,
            JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING,
            true,
            null,
            30,
            null,
            LoremActionPlace.EDITOR_POPUP
        ).also {
            it.setDataProvider {
                when {
                    CommonDataKeys.EDITOR.`is`(it) -> e.getData(CommonDataKeys.EDITOR)
                    PlatformCoreDataKeys.CONTEXT_COMPONENT.`is`(it) -> e.getData(CommonDataKeys.EDITOR)?.component
                    else -> null
                }
            }
        }

//        val contentComponent = popup.content
//        val shortcutSet = ActionManager.getInstance().getAction(LoremGeneratePopUpAction.ID).shortcutSet

//        object : AnAction() {
//            override fun actionPerformed(e: AnActionEvent) {
//                IdeFocusManager.getInstance(project).doWhenFocusSettlesDown {
//                    JBPopupFactory.getInstance().createActionGroupPopup(
//                        "Recent Actions",
//                        DefaultActionGroup("Recents", LoremRecentActionsManager.getInstance().getRecentActions()),
//                        e.dataContext,
//                        JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING,
//                        true,
//                        null,
//                        30,
//                        null,
//                        LoremActionPlace.EDITOR_POPUP
//                    ).also {
//                        it.setDataProvider {
//                            when {
//                                CommonDataKeys.EDITOR.`is`(it) -> e.getData(CommonDataKeys.EDITOR)
//                                PlatformCoreDataKeys.CONTEXT_COMPONENT.`is`(it) -> e.getData(CommonDataKeys.EDITOR)?.component
//                                else -> null
//                            }
//                        }
//                    }.showInBestPositionFor(e.dataContext)
//                }
//
//            }
//        }.registerCustomShortcutSet(
//            shortcutSet,
//            contentComponent
//        )

//        popup.setAdText(KeymapUtil.getShortcutsText(shortcutSet.shortcuts), SwingConstants.LEFT)
        popup.showInBestPositionFor(e.dataContext)
    }

    companion object {
        fun getInstance(project: Project) = project.service<LoremPopupManager>()
    }
}

