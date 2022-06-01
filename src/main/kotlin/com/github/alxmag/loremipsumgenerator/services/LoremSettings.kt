package com.github.alxmag.loremipsumgenerator.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service

@Service
class LoremSettings {

    val randomSentenceWordsNumber = 8

    val smallSentenceWords = 6
    val mediumSentenceWords = 12
    val bigSentenceWords = 24

    companion object {
        val instance = service<LoremSettings>()
    }
}