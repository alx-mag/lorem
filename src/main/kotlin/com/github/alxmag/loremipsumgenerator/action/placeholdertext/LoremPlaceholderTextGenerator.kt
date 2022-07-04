package com.github.alxmag.loremipsumgenerator.action.placeholdertext

import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.RandomUtils.getRandomIntBetween
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.thedeanda.lorem.LoremIpsum
import java.util.*

@Service(Service.Level.PROJECT)
class LoremPlaceholderTextGenerator {

    private val loremIpsum = LoremIpsum.getInstance()

    private val loremHistoryService = LoremPlaceholderTextSettings.getInstance()

    fun generateText(model: LoremPlaceholderTextModel): String = when (model.unit) {
        TextAmountUnit.WORD -> loremIpsum.getWords(model.amount)
        TextAmountUnit.SENTENCE -> sentences(model.amount)
        else -> throw UnsupportedOperationException()
    }

    private fun sentences(sentences: Int) = (1..sentences).joinToString(" ") {
        getRandomSentenceOfWords(loremHistoryService.wordsPerSentence)
    }

    private fun getRandomSentenceOfWords(minWords: Int, maxWords: Int): String {
        val words = when {
            minWords == maxWords -> minWords
            minWords > maxWords -> throw IllegalArgumentException("Min size must not be more than max size")
            else -> getRandomIntBetween(minWords, maxWords)
        }

        return loremIpsum.getWords(words).toSentence()
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
        fun getInstance(project: Project) = project.service<LoremPlaceholderTextGenerator>()
    }
}