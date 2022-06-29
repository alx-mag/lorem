package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.model.LoremTextModel
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.test.PlaceHolderAction
import com.github.alxmag.loremipsumgenerator.ui.LoremTextView
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.LoremDialogCaller.showLoremDialog
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.Separator

class LoremTextActionGroup : LoremPerformableActionGroupBase(
    LoremTextAction(),
    Separator(message("separator.recent")),
    PlaceHolderAction("5 Sentences")
)

class LoremTextAction : LoremActionBase(GenerateSentenceHandler())

class GenerateSentenceHandler : LoremActionHandlerBase() {
    var model = LoremTextModel(TextAmountUnit.PARAGRAPH, 5)
    override fun createText(editorContext: EditorContext): String? {
        val model = showLoremDialog(editorContext.project, ::model, ::LoremTextView)
            ?: return null

        return LoremIpsumService.getInstance(editorContext.project).generateText(model)
    }

}