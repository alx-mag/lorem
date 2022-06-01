package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.lorem.LoremEx
import lorem.Lorem
import lorem.LoremIpsum
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.util.*

@Service(Service.Level.PROJECT)
class LoremIpsumService : Lorem by LoremIpsum.getInstance(), LoremEx {

    private val loremSettings = LoremSettings.instance

    override fun getRandomSentence(): String = getWords(loremSettings.randomSentenceWordsNumber).toSentence()

    override fun getRandomSentenceOfWords(wordsNumber: Int) = getWords(wordsNumber).toSentence()

    private fun String.toSentence() = withDot().withCapitalization()

    private fun String.withCapitalization() = replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }

    private fun String.withDot() = takeIf { !it.endsWith(".") }
        ?.padEnd(1, '.')
        ?: this

    companion object {
        fun getInstance(project: Project) = project.service<LoremIpsumService>()
    }
}