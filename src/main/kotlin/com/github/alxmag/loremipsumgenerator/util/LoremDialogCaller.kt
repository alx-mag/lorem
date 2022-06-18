package com.github.alxmag.loremipsumgenerator.util

import com.github.alxmag.loremipsumgenerator.model.LoremModel
import com.github.alxmag.loremipsumgenerator.ui.*
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.panel
import kotlin.reflect.KMutableProperty0

object LoremDialogCaller {

    /**
     * Shows [LoremView] in dialog and saves selected model for next dialog calls.
     */
    fun <T : LoremModel> showLoremDialog(
        project: Project,
        state: KMutableProperty0<T>,
        createView: (T) -> LoremView<T>
    ): T? {
        val view = createView(state.get())
        object : LoremViewDialogWrapper<T>(project, view) {
            override fun createSouthAdditionalPanel() = panel {
                row {
                    link("Options") {

                    }
                }
            }
        }


        val model = createView(state.get())
            .toDialog(project)
            .takeIfOk()
            ?.getModel()
            ?: return null

        state.set(model)
        return model
    }
}