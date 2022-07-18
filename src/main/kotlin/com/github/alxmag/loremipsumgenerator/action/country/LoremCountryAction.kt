package com.github.alxmag.loremipsumgenerator.action.country

import com.github.alxmag.loremipsumgenerator.action.base.LoremActionHandler
import com.github.alxmag.loremipsumgenerator.action.base.LoremEditorAction
import com.github.alxmag.loremipsumgenerator.util.EditorContext
import com.thedeanda.lorem.LoremIpsum

private val lorem = LoremIpsum.getInstance()

class LoremCountryAction : LoremEditorAction(LoremCountryActionHandler())

class LoremCountryActionHandler : LoremActionHandler() {
    override fun createText(editorContext: EditorContext): String = lorem.country
}