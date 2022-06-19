package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.model.LoremTextModel
import com.github.alxmag.loremipsumgenerator.ui.LoremTextView
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.LoremDialogCaller.showLoremDialog
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremTextAction : LoremActionBase(GenerateSentenceHandler()) {
}

class GenerateSentenceHandler : LoremActionHandlerBase() {
    var model = LoremTextModel(TextAmountUnit.PARAGRAPH, 5, TextAmountUnit.SENTENCE)
    override fun createText(editorContext: EditorContext): String {
        showLoremDialog(editorContext.project, ::model, ::LoremTextView)
        return "Not implemented"
    }

}