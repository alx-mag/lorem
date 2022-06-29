package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.util.TextAmountUnit

class LoremTextModel(
    override var unit: TextAmountUnit,
    override var amount: Int
) : LoremModel