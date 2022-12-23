package com.github.alxmag.loremipsumgenerator.action.recent

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction

/**
 * [AnAction] can implement this interface to provide action id that should be memorized for recent actions list after execution.
 * @see [LoremRecentActionsManager]
 */
interface MemorizableActionIdProvider {
    fun getId(): String
}

/**
 * Simple implementation of [MemorizableActionIdProvider] that provides action id of implementing [AnAction].
 */
interface MemorizableAction : MemorizableActionIdProvider {
    override fun getId(): String = ActionManager.getInstance().getId(this as AnAction)
}