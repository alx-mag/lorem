package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.generate.LoremGenerateActionBase
import com.github.alxmag.loremipsumgenerator.generate.LoremGenerationActionHandlerBase
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.services.LoremSettings
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent

private val loremSettings = LoremSettings.instance

class RandomSentenceAction : LoremGenerateActionBase(GenerateSentenceHandler()) {

    class GenerateSentenceHandler : LoremGenerationActionHandlerBase() {
        override fun createText(editorContext: EditorContext) = LoremIpsumService.getInstance(editorContext.project)
            .getRandomSentence()
    }
}

abstract class ByWordsSentenceActionBase(
    private val name: String,
    private val wordsNumber: Int
) : LoremGenerateActionBase(ByWordsSentenceHandler(wordsNumber)) {

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

class ByWordsSentenceHandler(private val wordsNumber: Int) : LoremGenerationActionHandlerBase() {
    override fun createText(editorContext: EditorContext) = LoremIpsumService.getInstance(editorContext.project)
        .getRandomSentenceOfWords(wordsNumber)
}

