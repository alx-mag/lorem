package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.action.preview.LoremPreviewDialog
import com.github.alxmag.loremipsumgenerator.action.preview.LoremTemplate
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nls.Capitalization.Title

/**
 * Base implementation for lorem generation actions
 */
abstract class LoremActionHandler : EditorActionHandler.ForEachCaret() {

    override fun doExecute(editor: Editor, caret: Caret, dataContext: DataContext?) {
        val project = editor.project ?: return
        val editorContext = EditorContext(project, editor, caret, dataContext)
        val textToPut = createText(editorContext) ?: return

        runWriteAction {
            writeText(editorContext, textToPut)
        }

        val balloon = object : LoremEditorBalloon() {
            override fun createText(editorContext: EditorContext): String? = this@LoremActionHandler.createText(editorContext)

            override fun writeText(editorContext: EditorContext, text: String) {
                runUndoTransparentWriteAction {
                    this@LoremActionHandler.writeText(editorContext, text)
                }
            }
        }

        balloon.showEditorPopup(editorContext)
    }


    private fun writeText(editorContext: EditorContext, textToPut: String) {
        val document = editorContext.editor.document
        val caret = editorContext.caret
        // Replace selected text
        if (caret.hasSelection()) {
            document.replaceString(caret.selectionStart, caret.selectionEnd, textToPut)
            return
        }

        val textOffset = caret.offset
        document.insertString(textOffset, textToPut)
        // Select inputted text for ease later manipulations
        caret.setSelection(textOffset, textOffset + textToPut.length)

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

    companion object {
        fun create(operation: (EditorContext) -> String?) = object : LoremActionHandler() {
            override fun createText(editorContext: EditorContext) = operation(editorContext)
        }
    }
}

abstract class LoremPreviewDialogActionHandler(@Nls(capitalization = Title) private val dialogTitle: String) :
    LoremActionHandler() {
    override fun createText(editorContext: EditorContext): String? {
        val templates = getTemplates(editorContext)
        return LoremPreviewDialog(editorContext.project, dialogTitle, templates)
            .takeIfOk()
            ?.getText()
    }

    abstract fun getTemplates(editorContext: EditorContext): List<LoremTemplate>

    companion object {
        fun create(
            @Nls(capitalization = Title) dialogTitle: String,
            templatesProvider: (EditorContext) -> List<LoremTemplate>
        ) = object : LoremPreviewDialogActionHandler(dialogTitle) {
            override fun getTemplates(editorContext: EditorContext): List<LoremTemplate> =
                templatesProvider(editorContext)
        }
    }
}

/**
 * Generate action for multiple templates with preview dialog
 */
class SimpleLoremDialogAction(
    @Nls(capitalization = Title) dialogTitle: String,
    templatesProvider: (EditorContext) -> List<LoremTemplate>
) : EditorAction(LoremPreviewDialogActionHandler.create(dialogTitle, templatesProvider))

/**
 * Implementation for menu action
 */
class SimpleLoremGenerateTextAction(
    @Nls(capitalization = Title) name: String,
    createText: (EditorContext) -> String
) : EditorAction(LoremActionHandler.create(createText)) {

    init {
        templatePresentation.text = name
    }

    companion object {
        fun fromTemplate(template: LoremTemplate) = SimpleLoremGenerateTextAction(template.title) {
            template.generate()
        }
    }
}