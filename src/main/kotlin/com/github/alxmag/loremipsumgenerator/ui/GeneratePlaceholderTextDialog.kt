package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class GeneratePlaceholderTextDialog(
    project: Project,
    private val view: LoremTextView
) : DialogWrapper(project) {

    init {
        title = message("generate.placeholder.text.title")
        init()
    }

    override fun createCenterPanel(): JComponent = view.panel

    fun getModel() = view.createModel()

    override fun createSouthAdditionalPanel() = panel {
        row {
            link("Options") {  }
        }
    }
}

