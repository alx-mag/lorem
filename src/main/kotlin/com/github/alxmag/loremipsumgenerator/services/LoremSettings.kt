package com.github.alxmag.loremipsumgenerator.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service

@Service
class LoremSettings {

    val randomSentenceWordsNumber = 8

    val smallSentenceWords = 6
    val mediumSentenceWords = 12
    val bigSentenceWords = 24

    val smallParagraphSentences = 3
    val regularParagraphSentences = 6
    val longParagraphSentences = 12

    val commonTextAmountRange = (1 .. 1000_000)
    val wordsPerSentenceRange = (1 .. 9999)
    val sentencesPerParagraphRange = (1 .. 9999)

    companion object {
        val instance = service<LoremSettings>()
    }
}