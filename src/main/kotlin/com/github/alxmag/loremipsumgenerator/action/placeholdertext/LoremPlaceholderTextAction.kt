package com.github.alxmag.loremipsumgenerator.action.placeholdertext

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.services.LoremHistoryService
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
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
        val historyActions = LoremHistoryService.getInstance()
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

abstract class LoremPlaceholderTextActionHandler : LoremActionHandlerBase() {

    abstract val initialTextModel: LoremPlaceholderTextModel

    override fun createText(editorContext: EditorContext): String? {
        val model = getModel(editorContext) ?: return null
        LoremHistoryService.getInstance().saveLastTextModel(model)
        return LoremIpsumService.getInstance(editorContext.project).generateText(model)
    }

    protected abstract fun getModel(editorContext: EditorContext): LoremPlaceholderTextModel?

    class FromDialog : LoremPlaceholderTextActionHandler() {
        override val initialTextModel: LoremPlaceholderTextModel get() = LoremHistoryService.getInstance().preselectedLoremPlaceholderTextModel
        override fun getModel(editorContext: EditorContext) = LoremPlaceholderTextDialog(
            editorContext.project,
            LoremPlaceholderTextView(initialTextModel)
        )
            .takeIfOk()
            ?.getModel()
    }

    class FromHistory(override val initialTextModel: LoremPlaceholderTextModel) : LoremPlaceholderTextActionHandler() {
        override fun getModel(editorContext: EditorContext) = initialTextModel
    }
}