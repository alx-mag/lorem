package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.services.LoremSettings
import com.github.alxmag.loremipsumgenerator.services.LoremModelStateService
import com.github.alxmag.loremipsumgenerator.ui.LoremParagraphView
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.LoremDialogCaller.showLoremDialog
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.github.alxmag.loremipsumgenerator.util.lipsum

private val loremSettings = LoremSettings.instance

class LoremParagraphActionGroup : LoremPerformableActionGroupBase(LoremParagraphBySentencesAction())

class LoremShortParagraphAction : LoremActionBase(LoremParagraphHandler.BySentences.Short())
class LoremMediumParagraphAction : LoremActionBase(LoremParagraphHandler.BySentences.Medium())
class LoremLongParagraphAction : LoremActionBase(LoremParagraphHandler.BySentences.Long())

class LoremParagraphBySentencesAction : LoremActionBase(LoremParagraphHandler.ByParams())

abstract class LoremParagraphHandler : LoremActionHandlerBase() {

    open class BySentences(private val min: Int, private val max: Int) : LoremParagraphHandler() {
        constructor(number: Int) : this(number, number)

        override fun createText(editorContext: EditorContext): String = editorContext.lipsum
            .getParagraphs(min, max)

        class Short : BySentences(loremSettings.smallParagraphSentences)
        class Medium : BySentences(loremSettings.regularParagraphSentences)
        class Long : BySentences(loremSettings.longParagraphSentences)
    }

    class ByParams : LoremParagraphHandler() {
        override fun createText(editorContext: EditorContext): String? {
            val project = editorContext.project

            val model = showLoremDialog(
                project,
                LoremModelStateService.getInstance()::paragraph,
                ::LoremParagraphView
            ) ?: return null

            return when (model.unit) {
                TextAmountUnit.WORD -> LoremIpsumService.getInstance(project).getRandomSentenceOfWords(model.amount)
                else -> TODO()
            }
        }
    }
}