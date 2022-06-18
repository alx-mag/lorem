package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.model.LoremModel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

abstract class LoremViewDialogWrapper<T : LoremModel>(project: Project, private val view: LoremView<T>) :
    DialogWrapper(project) {

    init {
        title = message("put.text.title")
        this.init()
    }

    override fun createCenterPanel(): JComponent = view.createComponent()

    fun getModel() = view.createModel()

    override fun createSouthAdditionalPanel() = panel {
        row {
            link("Options") {  }
        }
    }
}

fun <T : LoremModel> LoremView<T>.toDialog(project: Project) = object : LoremViewDialogWrapper<T>(project, this) {

}