package com.github.alxmag.loremipsumgenerator.action.placeholdertext

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class LoremPlaceholderTextDialog(
    project: Project,
    private val view: LoremPlaceholderTextView
) : DialogWrapper(project) {

    init {
        title = message("generate.placeholder.text.title")
        // Shrink the dialog height if possible
        view.addUnitChangedListener {
            invokeLater {
                setSize(size.width, -1)
            }
        }
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

