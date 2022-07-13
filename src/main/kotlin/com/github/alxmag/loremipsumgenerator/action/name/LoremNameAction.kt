package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.action.preview.LoremPreviewDialog
import com.github.alxmag.loremipsumgenerator.template.LoremTemplatesManager
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremNameActionGroup : LoremPerformableActionGroupBase() {

    override val action: AnAction = object : EditorAction(object : LoremActionHandler() {
        override fun createText(editorContext: EditorContext): String? {
            val project = editorContext.project
            val nameTemplates = LoremTemplatesManager.getInstance(project).getNameTemplates()
            return LoremPreviewDialog(project, "Generate Name", nameTemplates)
                .takeIfOk()
                ?.getText()
        }
    }) {}

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val project = e?.project ?: return emptyArray()
        return LoremTemplatesManager.getInstance(project)
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

data class LoremNameModel(var pattern: NamePattern, var gender: Gender) {

    constructor() : this(NamePattern.FULL_NAME, Gender.MALE)

    fun createActionName() = buildString {
        append(pattern.visibleName)
        if (pattern == NamePattern.FIRSTNAME || pattern == NamePattern.FULL_NAME) {
            if (gender != Gender.ANY) {
                append(" | ")
                append(gender.visibleName)
            }
        }
    }
}

enum class NamePattern(val visibleName: String) {
    FIRSTNAME(message("firstname")),
    LASTNAME(message("lastname")),
    FULL_NAME(message("full.name"))
}

enum class Gender(val visibleName: String) {
    MALE(message("male")),
    FEMALE(message("female")),
    ANY(message("any"))
}