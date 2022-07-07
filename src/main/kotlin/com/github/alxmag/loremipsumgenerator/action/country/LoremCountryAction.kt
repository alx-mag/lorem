package com.github.alxmag.loremipsumgenerator.action.country

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.thedeanda.lorem.LoremIpsum

private val lorem = LoremIpsum.getInstance()

class LoremCountryAction : EditorAction(LoremCountryActionHandler())

class LoremCountryActionHandler : LoremActionHandler() {
    override fun createText(editorContext: EditorContext): String = lorem.country
}