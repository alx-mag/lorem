package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.action.recent.LoremMemorizeAction
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler

abstract class LoremEditorAction(defaultHandler: EditorActionHandler) : EditorAction(defaultHandler), LoremMemorizeAction