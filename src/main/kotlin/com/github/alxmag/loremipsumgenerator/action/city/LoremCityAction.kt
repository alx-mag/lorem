package com.github.alxmag.loremipsumgenerator.action.city

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.thedeanda.lorem.LoremIpsum

private val lorem = LoremIpsum.getInstance()

class LoremCityAction : EditorAction(LoremEmailActionHandler())

class LoremEmailActionHandler : LoremActionHandler() {
    override fun createText(editorContext: EditorContext): String = lorem.city
}