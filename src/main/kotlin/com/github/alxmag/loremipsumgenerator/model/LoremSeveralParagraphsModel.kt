package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremSeveralParagraphsModel(
    override var amount: Int,
    override var unit: TextAmountUnit,
    var wordsPerSentence: MinMax,
    var sentencesPerParagraph: MinMax = MinMax(5, 20)
) : LoremModel {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(5, TextAmountUnit.SENTENCE, MinMax(5, 10), MinMax(5, 10))
}