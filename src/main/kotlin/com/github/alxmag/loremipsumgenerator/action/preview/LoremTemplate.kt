package com.github.alxmag.loremipsumgenerator.action.preview

import com.intellij.ui.dsl.builder.Panel

interface LoremTemplate {
    val title: String

    fun generate(): String

    fun renderAdditionalParams(panel: Panel)
}