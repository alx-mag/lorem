package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremTextModel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class GeneratePlaceholderTextDialog(
    project: Project,
    private val view: LoremView<LoremTextModel>
) : DialogWrapper(project) {

    init {
        title = message("generate.placeholder.text.title")
        init()
    }

    override fun createCenterPanel(): JComponent = view.createComponent()

    fun getModel() = view.createModel()

    override fun createSouthAdditionalPanel() = panel {
        row {
            link("Options") {  }
        }
    }
}

