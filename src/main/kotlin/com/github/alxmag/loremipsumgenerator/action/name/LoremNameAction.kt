package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.action.preview.LoremPreviewDialog
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremNameActionGroup : LoremPerformableActionGroupBase() {

    override val action: AnAction = object : EditorAction(object : LoremActionHandler() {
        override fun createText(editorContext: EditorContext): String? {
            val project = editorContext.project
            val nameTemplates = LoremNameTemplatesManager.getInstance(project).getNameTemplates()
            return LoremPreviewDialog(project, "Generate Name", nameTemplates)
                .takeIfOk()
                ?.getText()
        }
    }) {}

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val project = e?.project ?: return emptyArray()
        return LoremNameTemplatesManager.getInstance(project)
            .getNameTemplates()
            .map { template ->
                object : EditorAction(LoremActionHandler.create { template.generate() }) {
                    init {
                        templatePresentation.text = template.title
                    }
                }
            }
            .toTypedArray()
    }
}