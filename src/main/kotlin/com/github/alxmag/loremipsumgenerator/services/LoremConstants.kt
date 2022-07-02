package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service

@Service
class LoremConstants {

    val randomSentenceWordsNumber = 8

    val commonTextAmountRange = (1 .. 1000_000)
    val wordsPerSentenceRange = (1 .. 9999)
    val sentencesPerParagraphRange = (1 .. 9999)

    val defaultWordsPerSentence = MinMax(5, 10)

    companion object {
        val instance = service<LoremConstants>()
    }
}