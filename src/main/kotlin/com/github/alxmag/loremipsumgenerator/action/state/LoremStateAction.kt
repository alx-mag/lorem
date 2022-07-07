package com.github.alxmag.loremipsumgenerator.action.state

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.thedeanda.lorem.LoremIpsum

private val lorem = LoremIpsum.getInstance()

class LoremStateAction : EditorAction(LoremStateActionHandler())

class LoremStateActionHandler : LoremActionHandler() {
    override fun createText(editorContext: EditorContext): String = lorem.stateFull
}