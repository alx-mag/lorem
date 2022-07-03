package com.github.alxmag.loremipsumgenerator.action.placeholdertext.ui

import com.github.alxmag.loremipsumgenerator.MyBundle
import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextSettings
import com.github.alxmag.loremipsumgenerator.ui.minMaxRow
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.takeIfOk
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.dsl.builder.panel

class WordsSettingsDialog(project: Project?, val model: WordsSettingsModel) : DialogWrapper(project) {

    private lateinit var minSpinner: JBIntSpinner
    private lateinit var maxSpinner: JBIntSpinner

    init {
        title = MyBundle.message("set.up.words.amount.title")
        init()
    }

    override fun createCenterPanel() = panel {
        minMaxRow(
            MyBundle.message("words.per.sentence.label"),
            (1 .. 9999),
            model.wordsPerSentence,
            configureMinSpinner = {
                minSpinner = it.component
            },
            configureMaxSpinner = {
                maxSpinner = it.component
            }
        )
    }

    override fun doValidate(): ValidationInfo? {
        if (maxSpinner.number < minSpinner.number) {
            return ValidationInfo("Must not be less than min value", maxSpinner)
        }
        return null
    }


    companion object {
        fun show(project: Project?): WordsSettingsModel? {
            val historyService = LoremPlaceholderTextSettings.getInstance()
            val model = WordsSettingsDialog(
                project,
                WordsSettingsModel(historyService.wordsPerSentence)
            )
                .takeIfOk()
                ?.model
                ?: return null

            historyService.wordsPerSentence = model.wordsPerSentence
            return model
        }
    }
}

class WordsSettingsModel(var wordsPerSentence: MinMax)