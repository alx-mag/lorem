package com.github.alxmag.loremipsumgenerator.lorem

import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel

interface LoremEx {
    fun getRandomSentence(): String

    fun getRandomSentenceOfWords(wordsNumber: Int): String = getRandomSentenceOfWords(wordsNumber, wordsNumber)

    fun getRandomSentenceOfWords(minWords: Int, maxWords: Int): String

    fun getParagraph(paragraphModel: LoremParagraphModel): String
}