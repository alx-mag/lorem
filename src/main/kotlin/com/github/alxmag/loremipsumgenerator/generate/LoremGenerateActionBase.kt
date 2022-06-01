package com.github.alxmag.loremipsumgenerator.generate

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

abstract class LoremGenerateActionBase(handler: EditorActionHandler) : EditorAction(handler) {

    // Finalize update to avoid accidental override
    final override fun update(e: AnActionEvent) {
        doUpdate(e)
        super.update(e)
    }

    override fun update(editor: Editor, presentation: Presentation, dataContext: DataContext) {
        super.update(editor, presentation, dataContext)

    }

    open fun doUpdate(e: AnActionEvent) {

    }
}