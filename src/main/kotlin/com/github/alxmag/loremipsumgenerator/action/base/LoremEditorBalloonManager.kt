package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.LoremGeneratePopUpAction
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.keymap.KeymapUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.dsl.builder.panel

@Service(Service.Level.PROJECT)
class LoremEditorBalloonManager {

    private var editorBalloon: LoremEditorBalloon? = null

    fun show(handler: TextHandler, editorContext: EditorContext) {
        editorBalloon = LoremEditorBalloon(handler, editorContext)
        editorBalloon?.showEditorPopup()
    }

    fun isShowing() = editorBalloon?.isDisposed == false

    fun repeatAction() = editorBalloon?.takeIf { !it.isDisposed }?.repeatAction() != null

    private class LoremEditorBalloon(private val textHandler: TextHandler, private val editorContext: EditorContext) {

        private lateinit var balloon: Balloon

        val isDisposed get() = balloon.isDisposed

        fun showEditorPopup() {
            val popupFactory = JBPopupFactory.getInstance()
            val panel = panel {
                row {
                    link(message("action.lorem.Regenerate.text")) {
                        repeatAction()
                    }
                    // Shortcut hint
                    KeymapUtil.getShortcutText(LoremGeneratePopUpAction.ID)
                        .takeIf { it.isNotEmpty() }
                        ?.let(::comment)
                }
            }

            balloon = popupFactory
                .createBalloonBuilder(panel)
                .setHideOnClickOutside(true)
                .setHideOnKeyOutside(true)
                .setBlockClicksThroughBalloon(true)
                .setFillColor(panel.background)
                .setAnimationCycle(0)
                .setRequestFocus(false)
                .createBalloon()

            balloon.show(popupFactory.guessBestPopupLocation(editorContext.editor), Balloon.Position.below)
        }

        fun repeatAction() {
            balloon.hide()
            val text = textHandler.createText(editorContext) ?: return
            runUndoTransparentWriteAction {
                textHandler.writeText(editorContext, text)
            }
            showEditorPopup()
        }
    }

    interface TextHandler {
        fun createText(editorContext: EditorContext): String?
        fun writeText(editorContext: EditorContext, text: String)
    }

    companion object {
        fun getInstance(project: Project) = project.service<LoremEditorBalloonManager>()
    }
}
