package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.lorem.LoremEx
import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextModel
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.RandomUtils
import com.github.alxmag.loremipsumgenerator.util.RandomUtils.getRandomIntBetween
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.github.alxmag.loremipsumgenerator.util.randomBetween
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import lorem.LoremIpsum
import java.util.*

@Service(Service.Level.PROJECT)
class LoremIpsumService : LoremIpsum("/lorem"), LoremEx {

    private val loremSettings = LoremSettings.instance
    private val loremHistoryService = LoremHistoryService.getInstance()

    fun generateText(model: LoremPlaceholderTextModel) = when (model.unit) {
        TextAmountUnit.WORD -> getWords(model.amount)
        TextAmountUnit.SENTENCE -> _sentences(model.amount)
        else -> throw UnsupportedOperationException()
    }

    private fun _sentence() = getRandomSentenceOfWords(loremHistoryService.wordsPerSentence)
    private fun _sentences(sentences: Int) = (1..sentences).joinToString(" ") {
        _sentence()
    }

    private fun _paragraphs(paragraphs: Int) = (1..paragraphs).joinToString("\n") {
        _sentences(loremHistoryService.sentencesPerParagraph.randomBetween())
    }

    override fun getRandomSentence(): String = getWords(loremSettings.randomSentenceWordsNumber).toSentence()

    override fun getParagraph(paragraphModel: LoremParagraphModel) = when (paragraphModel.unit) {
        TextAmountUnit.SENTENCE -> (1..paragraphModel.amount).map {
            getRandomIntBetween(paragraphModel.wordsPerSentence.min, paragraphModel.wordsPerSentence.max)
        }.joinToString(separator = " ", transform = ::getRandomSentenceOfWords)

        TextAmountUnit.WORD -> RandomUtils.numberToTerms(
            paragraphModel.amount,
            paragraphModel.wordsPerSentence.min,
            paragraphModel.wordsPerSentence.max
        ).joinToString(separator = " ", transform = ::getRandomSentenceOfWords)

        else -> throw UnsupportedOperationException("Unsupported unit ${paragraphModel.unit}")
    }

    override fun getRandomSentenceOfWords(minWords: Int, maxWords: Int): String {
        val words = when {
            minWords == maxWords -> minWords
            minWords > maxWords -> throw IllegalArgumentException("Min size must not be more than max size")
            else -> getRandomIntBetween(minWords, maxWords)
        }

        return getWords(words, false).toSentence()
    }

    private fun getRandomSentenceOfWords(words: MinMax) = getRandomSentenceOfWords(words.min, words.max)

    private fun String.toSentence() = withDot().withCapitalization()

    private fun String.withCapitalization() = replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }

    private fun String.withDot() = takeIf { !it.endsWith(".") }
        ?.let { "$it." }
        ?: this

    companion object {
        fun getInstance(project: Project) = project.service<LoremIpsumService>()
    }
}