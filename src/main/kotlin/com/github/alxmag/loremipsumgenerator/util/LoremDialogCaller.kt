package com.github.alxmag.loremipsumgenerator.util

import com.github.alxmag.loremipsumgenerator.services.LoremModel
import com.github.alxmag.loremipsumgenerator.ui.*
import com.intellij.openapi.project.Project
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
        val model = createView(state.get())
            .toDialog(project)
            .takeIfOk()
            ?.getModel()
            ?: return null

        state.set(model)
        return model
    }
}