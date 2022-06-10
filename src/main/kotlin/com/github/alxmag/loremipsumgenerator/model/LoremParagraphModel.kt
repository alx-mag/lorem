package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.services.LoremModel
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

open class LoremParagraphModel(
    override var amount: Int,
    override var unit: TextAmountUnit,
    var wordsPerSentence: MinMax
) : LoremModel {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(5, TextAmountUnit.SENTENCE, MinMax(5, 10))
}