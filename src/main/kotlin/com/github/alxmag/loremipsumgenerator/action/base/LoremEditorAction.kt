package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.action.recent.LoremMemorizeAction
import com.github.alxmag.loremipsumgenerator.util.LoremActionPlace
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import org.jetbrains.annotations.Nls

abstract class LoremEditorAction(
    defaultHandler: EditorActionHandler,
    @Nls(capitalization = Nls.Capitalization.Title) baseName: String? = null
) : EditorAction(defaultHandler), LoremMemorizeAction {
    init {
        if (baseName != null) {
            addTextOverride(LoremActionPlace.EDITOR_POPUP, baseName)
            templatePresentation.text = "Lorem: $baseName"
        }
    }

    final override fun addTextOverride(place: String, text: String) = super.addTextOverride(place, text)
}