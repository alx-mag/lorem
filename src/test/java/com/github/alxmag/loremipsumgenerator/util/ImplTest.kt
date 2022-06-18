package com.github.alxmag.loremipsumgenerator.util

import org.junit.jupiter.api.Test

internal class ImplTest {

    @Test
    fun convertTo() {
        val convert = TextAmountConverter(ConverterData.create()).convert(MinMax(1), TextAmountUnit.PARAGRAPH, TextAmountUnit.WORD)
        println(convert)
    }
}