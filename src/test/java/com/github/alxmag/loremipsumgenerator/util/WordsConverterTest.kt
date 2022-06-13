package com.github.alxmag.loremipsumgenerator.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WordsConverterTest {

    @Test
    fun convertTo() {
        val (min, max) = WordsConverter(
            wordsPerSentence = MinMax(2, 4),
            sentencesPerParagraph = MinMax(5, 10)
        ).convertTo(40, TextAmountUnit.SENTENCE)
        println(min)
        println(max)
    }

    @Test
    fun testSentenceConverter() {
        val res = SentencesConverter(
            wordsPerSentence = MinMax(2, 4),
            sentencesPerParagraph = MinMax(5, 10)
        ).convertTo(40, TextAmountUnit.WORD)

        println(res)
    }
}