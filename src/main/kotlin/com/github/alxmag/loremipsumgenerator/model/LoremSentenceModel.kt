package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremSentenceModel(override var amount: Int, override var unit: TextAmountUnit) : LoremModel {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(5, TextAmountUnit.SENTENCE)
}