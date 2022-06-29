package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremTextModel(
    override var unit: TextAmountUnit,
    override var amount: Int
) : LoremModel {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(TextAmountUnit.SENTENCE, 5)
}