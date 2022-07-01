package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.model.LoremTextModel
import com.github.alxmag.loremipsumgenerator.services.LoremHistoryService
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.ui.GeneratePlaceholderTextDialog
import com.github.alxmag.loremipsumgenerator.ui.LoremTextView
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.editor.actionSystem.EditorAction

class LoremTextActionGroup : LoremPerformableActionGroupBase(LoremTextAction(LoremTextActionHandler.FromDialog())) {

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val historyActions = LoremHistoryService.getInstance()
            .textModelsHistory
            .map(LoremTextActionHandler::FromHistory)
            .map(::LoremTextAction)

        return buildList {
            if (historyActions.isNotEmpty()) {
                add(Separator(message("separator.recent")))
            }
            addAll(historyActions)
        }.toTypedArray()
    }
}

class LoremTextAction(handler: LoremTextActionHandler) : EditorAction(handler) {
    init {
        val textModel = handler.initialTextModel
        templatePresentation.text = textModel.amount.toString() + " " + textModel.unit.visibleName(true)
    }
}

abstract class LoremTextActionHandler : LoremActionHandlerBase() {

    abstract val initialTextModel: LoremTextModel

    override fun createText(editorContext: EditorContext): String? {
        val model = getModel(editorContext) ?: return null
        LoremHistoryService.getInstance().saveLastTextModel(model)
        return LoremIpsumService.getInstance(editorContext.project).generateText(model)
    }

    protected abstract fun getModel(editorContext: EditorContext): LoremTextModel?

    class FromDialog : LoremTextActionHandler() {
        override val initialTextModel: LoremTextModel get() = LoremHistoryService.getInstance().preselectedLoremTextModel
        override fun getModel(editorContext: EditorContext) = GeneratePlaceholderTextDialog(
            editorContext.project,
            LoremTextView(initialTextModel)
        )
            .takeIfOk()
            ?.getModel()
    }

    class FromHistory(override val initialTextModel: LoremTextModel) : LoremTextActionHandler() {
        override fun getModel(editorContext: EditorContext) = initialTextModel
    }
}