package com.github.alxmag.loremipsumgenerator.action.placeholdertext.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextGenerator
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextModel
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextSettings
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremPlaceholderTextActionGroup : LoremPerformableActionGroupBase(
    LoremPlaceholderTextAction(
        LoremPlaceholderTextActionHandler.FromDialog()
    )
) {

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val historyActions = LoremPlaceholderTextSettings.getInstance()
            .textModelsHistory
            .map(LoremPlaceholderTextActionHandler::FromHistory)
            .map(::LoremPlaceholderTextAction)

        return buildList {
            if (historyActions.isNotEmpty()) {
                add(Separator(message("separator.recent")))
            }
            addAll(historyActions)
        }.toTypedArray()
    }
}

class LoremPlaceholderTextAction(handler: LoremPlaceholderTextActionHandler) : EditorAction(handler) {
    init {
        val textModel = handler.initialTextModel
        templatePresentation.text = textModel.amount.toString() + " " + textModel.unit.visibleName(true)
    }
}

abstract class LoremPlaceholderTextActionHandler : LoremActionHandler.ByModel<LoremPlaceholderTextModel>() {

    /**
     * Required for action presentation
     */
    abstract val initialTextModel: LoremPlaceholderTextModel

    override fun generateText(editorContext: EditorContext, model: LoremPlaceholderTextModel): String {
        return LoremPlaceholderTextGenerator
            .getInstance(editorContext.project)
            .generateText(model)
    }

    override fun afterModelProvided(model: LoremPlaceholderTextModel) {
        LoremPlaceholderTextSettings.getInstance().saveLastTextModel(model)
    }

    class FromDialog : LoremPlaceholderTextActionHandler() {

        override val initialTextModel: LoremPlaceholderTextModel = LoremPlaceholderTextSettings.getInstance()
            .preselectedLoremPlaceholderTextModel


        override fun getModel(editorContext: EditorContext) = LoremPlaceholderTextDialog(
            editorContext.project,
            LoremPlaceholderTextView(editorContext.project, initialTextModel)
        )
            .takeIfOk()
            ?.getModel()
    }

    class FromHistory(override val initialTextModel: LoremPlaceholderTextModel) : LoremPlaceholderTextActionHandler() {
        override fun getModel(editorContext: EditorContext) = initialTextModel
    }
}