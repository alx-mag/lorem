package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

/**
 * Base implementation for lorem generation actions
 */
abstract class LoremActionHandlerBase : EditorActionHandler.ForEachCaret() {

    override fun doExecute(editor: Editor, caret: Caret, dataContext: DataContext?) {
        val project = editor.project ?: return
        val editorContext = EditorContext(project, editor, caret, dataContext)
        val textToPut = createText(editorContext) ?: return
        runWriteAction {
            val document = editor.document

            // Replace selected text
            if (caret.hasSelection()) {
                document.replaceString(caret.selectionStart, caret.selectionEnd, textToPut)
                return@runWriteAction
            }

            val textOffset = caret.offset
            document.insertString(textOffset, textToPut)
            // Select inputted text for ease later manipulations
            caret.setSelection(textOffset, textOffset + textToPut.length)
        }
    }

    protected abstract fun createText(editorContext: EditorContext): String?
}