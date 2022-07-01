package com.github.alxmag.loremipsumgenerator.action.placeholdertext

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

data class LoremPlaceholderTextModel(var unit: TextAmountUnit, var amount: Int) {

    /**
     * Need no-args constructor for serialization purpose
     */
    @Suppress("unused")
    constructor() : this(TextAmountUnit.SENTENCE, 5)
}