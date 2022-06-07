package com.github.alxmag.loremipsumgenerator.action.base

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

/**
 * Base editor action for lorem text generation
 */
abstract class LoremActionBase(handler: EditorActionHandler) : EditorAction(handler) {

    // Finalize update to avoid accidental override
    final override fun update(e: AnActionEvent) {
        doUpdate(e)
        super.update(e)
    }

    override fun update(editor: Editor, presentation: Presentation, dataContext: DataContext) {
        super.update(editor, presentation, dataContext)

    }

    /**
     * Override to customize [update] behaviour
     */
    protected open fun doUpdate(e: AnActionEvent) {

    }
}