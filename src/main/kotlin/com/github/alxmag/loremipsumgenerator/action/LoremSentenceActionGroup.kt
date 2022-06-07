package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandlerBase
import com.github.alxmag.loremipsumgenerator.action.base.LoremPerformableActionGroupBase
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.services.LoremSettings
import com.github.alxmag.loremipsumgenerator.services.LoremModelStateService
import com.github.alxmag.loremipsumgenerator.ui.LoremSentenceView
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.github.alxmag.loremipsumgenerator.util.LoremDialogCaller.showLoremDialog
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent

private val loremSettings = LoremSettings.instance

class LoremSentenceActionGroup : LoremPerformableActionGroupBase(RandomSentenceAction())

class RandomSentenceAction : LoremActionBase(GenerateSentenceHandler()) {

    class GenerateSentenceHandler : LoremActionHandlerBase() {
        override fun createText(editorContext: EditorContext): String? {
            val project = editorContext.project


            val model = showLoremDialog(
                project,
                LoremModelStateService.getInstance()::sentence,
                ::LoremSentenceView
            ) ?: return null

            return when (val unit = model.unit) {
                TextAmountUnit.WORD -> LoremIpsumService.getInstance(project).getRandomSentenceOfWords(model.amount)
                TextAmountUnit.CHARACTER -> TODO()
                else -> throw UnsupportedOperationException("Unsupported unit '$unit'")
            }
        }
    }
}

abstract class ByWordsSentenceActionBase(
    private val name: String,
    private val wordsNumber: Int
) : LoremActionBase(ByWordsSentenceHandler(wordsNumber)) {

    override fun doUpdate(e: AnActionEvent) {
        if (e.place == ActionPlaces.POPUP) {
            e.presentation.text = message("random.sentence.action.popup.text", name, wordsNumber)
            e.presentation.description = message("random.sentence.action.popup.description", wordsNumber)
        }
    }
}

class RandomSentenceActionShort : ByWordsSentenceActionBase(message("title.small"), loremSettings.smallSentenceWords)
class RandomSentenceActionMedium : ByWordsSentenceActionBase(message("title.medium"), loremSettings.mediumSentenceWords)
class RandomSentenceActionLong : ByWordsSentenceActionBase(message("title.long"), loremSettings.bigSentenceWords)

class ByWordsSentenceHandler(private val wordsNumber: Int) : LoremActionHandlerBase() {
    override fun createText(editorContext: EditorContext) = LoremIpsumService.getInstance(editorContext.project)
        .getRandomSentenceOfWords(wordsNumber)
}

