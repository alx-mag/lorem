package com.github.alxmag.loremipsumgenerator.action.recent

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction

interface LoremMemorizableAction {
    fun getId(): String

    interface BySelfId : LoremMemorizableAction {
        override fun getId(): String = ActionManager.getInstance().getId(this as AnAction)
    }
}