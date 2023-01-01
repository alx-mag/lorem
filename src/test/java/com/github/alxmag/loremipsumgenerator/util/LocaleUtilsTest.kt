package com.github.alxmag.loremipsumgenerator.util

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import java.util.*

class LocaleUtilsTest : BasePlatformTestCase() {

    fun testDataFakerLocales() {
        val dataFakerLocales = LocaleUtils.dataFakerLocales
        println(dataFakerLocales)
    }

    fun testLocaleDisplayName() {
        TestCase.assertEquals(
            "Chinese (Simplified, Singapore)",
            LocaleUtils.getLocaleDisplayName(Locale.forLanguageTag("zh-Hans-SG"), Locale.ENGLISH)
        )

        println(LocaleUtils.getLocaleDisplayName(Locale.forLanguageTag("zh-Hans-SG"), Locale.forLanguageTag("ru-RU")))
    }
}