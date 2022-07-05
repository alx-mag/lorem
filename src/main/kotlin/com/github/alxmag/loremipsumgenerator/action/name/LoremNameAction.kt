package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremNameActionGroup : LoremPerformableActionGroupBase() {

    override val action: AnAction = object : EditorAction(LoremNameActionHandler()) {}

    // TODO
    override fun getChildren(e: AnActionEvent?): Array<AnAction> = emptyArray()
}

class LoremNameActionHandler : LoremActionHandler.ByModel<LoremNameModel>() {

    override fun getModel(editorContext: EditorContext) = LoremNameDialog(
        LoremNameSettings.getInstance().state.preselectedNameModel,
        editorContext.project
    )
        .takeIfOk()
        ?.getModel()

    override fun generateText(editorContext: EditorContext, model: LoremNameModel): String {
        return LoremNameGenerator.getInstance().generateName(model)
    }

    override fun afterModelProvided(model: LoremNameModel) {
        LoremNameSettings.getInstance().applyModelToHistory(model)
    }
}


data class LoremNameModel(var pattern: NamePattern, var gender: Gender) {

    constructor() : this(NamePattern.FULL_NAME, Gender.MALE)
}

enum class NamePattern(val visibleName: String) {
    FIRSTNAME("First Name"),
    LASTNAME("Last Name"),
    FULL_NAME("Full Name")
}

enum class Gender(val visibleName: String) {
    MALE("Male"),
    FEMALE("Female"),
    ANY("Any")
}