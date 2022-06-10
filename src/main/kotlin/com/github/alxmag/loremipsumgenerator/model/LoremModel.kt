package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

interface LoremModel {
    var amount: Int
    var unit: TextAmountUnit
}