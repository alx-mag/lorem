package com.github.alxmag.loremipsumgenerator.util

import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

class EditorContext(
    val project: Project,
    val editor: Editor,
    val caret: Caret,
    val dataContext: DataContext?
)

val EditorContext.lipsum get() = LoremIpsumService.getInstance(project)