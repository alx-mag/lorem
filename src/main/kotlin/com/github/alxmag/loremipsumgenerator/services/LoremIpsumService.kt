package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.lorem.LoremEx
import com.github.alxmag.loremipsumgenerator.ui.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.util.RandomUtils
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import lorem.Lorem
import lorem.LoremIpsum
import java.util.*

@Service(Service.Level.PROJECT)
class LoremIpsumService : Lorem by LoremIpsum.getInstance(), LoremEx {

    private val loremSettings = LoremSettings.instance

    override fun getRandomSentence(): String = getWords(loremSettings.randomSentenceWordsNumber).toSentence()

    override fun getParagraph(paragraphModel: LoremParagraphModel) = when (paragraphModel.unit) {
        TextAmountUnit.SENTENCE -> sequence {
            repeat(paragraphModel.amount) {
                val sentence = getRandomSentenceOfWords(5, 8)
                yield(sentence)
            }
        }.joinToString(" ")
        TextAmountUnit.WORD -> RandomUtils.numberToTerms(paragraphModel.amount, 5, 8)
            .joinToString(" ", transform = ::getRandomSentenceOfWords)
        else -> throw UnsupportedOperationException("Unsupported unit ${paragraphModel.unit}")
    }

    override fun getRandomSentenceOfWords(minWords: Int, maxWords: Int): String {
        val words = when {
            minWords == maxWords -> minWords
            minWords > maxWords -> throw IllegalArgumentException("Min size must not be more than max size")
            else -> Random().nextInt(maxWords - minWords) + minWords
        }

        return getWords(words).toSentence()
    }

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