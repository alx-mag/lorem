package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.services.LoremModel
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent

class LoremViewDialogWrapper<T : LoremModel>(project: Project, private val view: LoremView<T>) :
    DialogWrapper(project) {

    init {
        title = message("put.text.title")
        this.init()
    }

    override fun createCenterPanel(): JComponent = view.createComponent()

    fun getModel() = view.createModel()
}

fun <T : LoremModel> LoremView<T>.toDialog(project: Project) = LoremViewDialogWrapper(project, this)