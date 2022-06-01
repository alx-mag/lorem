package com.github.alxmag.loremipsumgenerator.lorem

interface LoremEx {
    fun getRandomSentence(): String

    fun getRandomSentenceOfWords(wordsNumber: Int): String
}