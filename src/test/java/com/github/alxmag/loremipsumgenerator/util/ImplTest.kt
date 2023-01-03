package com.github.alxmag.loremipsumgenerator.util

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Ignore
import org.junit.jupiter.api.Test

// TODO fix it
@Ignore
internal class ImplTest : BasePlatformTestCase() {

    fun testConvert() {
        val convert = TextAmountConverter(ConverterData.create()).convert(MinMax(1), TextAmountUnit.PARAGRAPH, TextAmountUnit.WORD)
        println(convert)
    }
}