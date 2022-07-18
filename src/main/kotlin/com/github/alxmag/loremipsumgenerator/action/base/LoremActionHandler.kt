package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.service.FakerManager
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.runUndoTransparentWriteAction
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.thedeanda.lorem.LoremIpsum
import net.datafaker.Faker

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

}

abstract class LoremTextFactoryActonHandler<T> : LoremActionHandler() {
    override fun createText(editorContext: EditorContext): String? {
        val factory = getFactory(editorContext)
        return doCreateText(factory, editorContext)
    }

    protected abstract fun getFactory(editorContext: EditorContext): T

    abstract fun doCreateText(factory: T, editorContext: EditorContext): String?

    companion object {
        fun simpleFakerHandler(generate: (Faker) -> String?) = object : LoremTextFactoryActonHandler<Faker>() {
            override fun getFactory(editorContext: EditorContext): Faker = FakerManager.getInstance(editorContext.project).getFaker()
            override fun doCreateText(factory: Faker, editorContext: EditorContext) = generate(factory)
        }

        fun simpleLoremIpsumHandler(generate: (LoremIpsum) -> String?) = object : LoremTextFactoryActonHandler<LoremIpsum>() {
            override fun getFactory(editorContext: EditorContext) = LoremIpsum()
            override fun doCreateText(factory: LoremIpsum, editorContext: EditorContext) = generate(factory)
        }
    }
}