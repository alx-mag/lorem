package com.github.alxmag.loremipsumgenerator.action.placeholdertext.ui

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextGenerator
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextModel
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextSettings
import com.github.alxmag.loremipsumgenerator.action.recent.LoremMemorizableAction
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremPlaceholderTextActionGroup : LoremPerformableActionGroupBase.WithRecentActions() {

    override val action: AnAction = LoremPlaceholderTextAction(LoremPlaceholderTextActionHandler.FromDialog())

    override fun getHistoryActions(): List<AnAction> = LoremPlaceholderTextSettings.getInstance()
        .textModelsHistory
        .map(LoremPlaceholderTextActionHandler::FromHistory)
        .map { LoremPlaceholderTextAction.FromHistory(this, it) }

    private open class LoremPlaceholderTextAction(handler: LoremPlaceholderTextActionHandler) : EditorAction(handler) {
        init {
            val textModel = handler.initialTextModel
            templatePresentation.text = textModel.amount.toString() + " " + textModel.unit.visibleName(true)
        }

        class FromHistory(parentGroup: LoremMemorizableAction, handler: LoremPlaceholderTextActionHandler) :
            LoremPlaceholderTextAction(handler), LoremMemorizableAction by parentGroup
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

        override val initialTextModel: LoremPlaceholderTextModel
            // Should be stateless because text model is roaming value
            get() = LoremPlaceholderTextSettings.getInstance()
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