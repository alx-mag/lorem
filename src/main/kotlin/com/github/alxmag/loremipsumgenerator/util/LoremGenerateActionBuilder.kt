package com.github.alxmag.loremipsumgenerator.util

import com.github.alxmag.loremipsumgenerator.generate.LoremGenerateActionBase
import com.github.alxmag.loremipsumgenerator.generate.LoremGenerationActionHandlerBase
import com.github.alxmag.loremipsumgenerator.generate.LoremPerformableActionGroupBase
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

class LoremGenerateActionBuilder {

    private var configureTemplatePresentation: Presentation.() -> Unit = { }
    private var handler: EditorActionHandler? = null
    private var subActions: List<AnAction> = emptyList()

    fun templatePresentation(block: Presentation.() -> Unit) {
        configureTemplatePresentation = block
    }

    fun generateText(block: (EditorContext) -> String?) {
        handler = object : LoremGenerationActionHandlerBase() {
            override fun createText(editorContext: EditorContext): String? = block(editorContext)
        }
    }

    fun subAction(block: LoremGenerateActionBuilder.() -> Unit) {
        val subAction = loremAction(block)
        subActions = subActions + subAction
    }

    private fun buildPerformableGroup(): ActionGroup {
        val performAction = buildAction(false)
        return object : LoremPerformableActionGroupBase(performAction, subActions) {
            init {
                templatePresentation.configureTemplatePresentation()
            }
        }
    }

    private fun buildAction(configurePresentation: Boolean = true): AnAction {
        return object : LoremGenerateActionBase(
            handler ?: throw IllegalStateException("Handler must not be null")
        ) {
            init {
                if (configurePresentation) {
                    templatePresentation.configureTemplatePresentation()
                }
            }
        }
    }

    private fun build() = if (subActions.isEmpty()) {
        buildAction()
    } else {
        buildPerformableGroup()
    }

    companion object {
        fun loremAction(block: LoremGenerateActionBuilder.() -> Unit): AnAction {
            val builder = LoremGenerateActionBuilder()
            builder.block()
            return builder.build()
        }
    }
}

