package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremNameActionGroup : LoremPerformableActionGroupBase() {

    override val action: AnAction = object : EditorAction(LoremNameActionHandler.FromDialog()) {}

    override fun getChildren(e: AnActionEvent?): Array<AnAction> = sequenceOf(
        LoremNameModel(NamePattern.FIRSTNAME, Gender.ANY),
        LoremNameModel(NamePattern.FIRSTNAME, Gender.MALE),
        LoremNameModel(NamePattern.FIRSTNAME, Gender.FEMALE),
        LoremNameModel(NamePattern.LASTNAME, Gender.ANY),
        LoremNameModel(NamePattern.FULL_NAME, Gender.ANY),
        LoremNameModel(NamePattern.FULL_NAME, Gender.MALE),
        LoremNameModel(NamePattern.FULL_NAME, Gender.FEMALE)
    ).map { model ->
        object : EditorAction(LoremNameActionHandler.FromModel(model)) {
            init {
                templatePresentation.text = model.createActionName()
            }
        }
    }.toList().toTypedArray()
}

abstract class LoremNameActionHandler : LoremActionHandler.ByModel<LoremNameModel>() {

    override fun generateText(editorContext: EditorContext, model: LoremNameModel): String {
        return LoremNameGenerator.getInstance().generateName(model)
    }

    override fun afterModelProvided(model: LoremNameModel) {
        LoremNameSettings.getInstance().applyModelToHistory(model)
    }

    class FromDialog : LoremNameActionHandler() {
        override fun getModel(editorContext: EditorContext) = LoremNameDialog(
            LoremNameSettings.getInstance().state.preselectedNameModel,
            editorContext.project
        )
            .takeIfOk()
            ?.getModel()
    }

    class FromModel(private val model: LoremNameModel) : LoremNameActionHandler() {
        override fun getModel(editorContext: EditorContext): LoremNameModel = model

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