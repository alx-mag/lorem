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
abstract class LoremActionHandler : EditorActionHandler.ForEachCaret() {

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

    abstract class ByModel<T> : LoremActionHandler() {
        final override fun createText(editorContext: EditorContext): String? {
            val model = getModel(editorContext) ?: return null
            afterModelProvided(model)
            return generateText(editorContext, model)
        }


        /**
         * Provides generation model. This can be provided via dialog or programmaticaly
         */
        protected abstract fun getModel(editorContext: EditorContext): T?

        /**
         * Used to store model into history
         */
        protected abstract fun afterModelProvided(model: T)

        protected abstract fun generateText(editorContext: EditorContext, model: T): String
    }
}

