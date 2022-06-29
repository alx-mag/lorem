package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.services.LoremModelStateService
import com.github.alxmag.loremipsumgenerator.test.PlaceHolderAction
import com.github.alxmag.loremipsumgenerator.ui.GeneratePlaceholderTextDialog
import com.github.alxmag.loremipsumgenerator.ui.LoremTextView
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.actionSystem.Separator

class LoremTextActionGroup : LoremPerformableActionGroupBase(
    LoremTextAction(),
    Separator(message("separator.recent")),
    PlaceHolderAction("5 Sentences")
)

class LoremTextAction : LoremActionBase(GenerateSentenceHandler())

class GenerateSentenceHandler : LoremActionHandlerBase() {
    override fun createText(editorContext: EditorContext): String? {
        val modelStateProp = LoremModelStateService.getInstance()::loremTextModel
        val model = GeneratePlaceholderTextDialog(
            editorContext.project,
            LoremTextView(modelStateProp.get())
        )
            .takeIfOk()
            ?.getModel()
            ?: return null

        modelStateProp.set(model)
        return LoremIpsumService.getInstance(editorContext.project).generateText(model)
    }
}