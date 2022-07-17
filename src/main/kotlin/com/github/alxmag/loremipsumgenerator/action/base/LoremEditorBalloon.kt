package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.dsl.builder.panel

abstract class LoremEditorBalloon {

    private lateinit var balloon: Balloon

    fun showEditorPopup(editorContext: EditorContext) {
        val popupFactory = JBPopupFactory.getInstance()
        val panel = panel {
            row {
                link(message("action.lorem.Regenerate.text")) {
                    balloon.hide()

                    val text = createText(editorContext) ?: return@link
                    runUndoTransparentWriteAction {
                        writeText(editorContext, text)
                    }
                    showEditorPopup(editorContext)
                }
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

    abstract fun createText(editorContext: EditorContext): String?

    abstract fun writeText(editorContext: EditorContext, text: String)
}