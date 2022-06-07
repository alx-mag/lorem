package com.github.alxmag.loremipsumgenerator.action.base

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup

/**
 * Base class for lorem action groups.
 * This group is executable by delegating performance to provided [action].
 */
abstract class LoremPerformableActionGroupBase(
    private val action: AnAction,
    subActions: List<AnAction>
) : DefaultActionGroup(subActions) {

    constructor(action: AnAction, vararg subActions: AnAction) : this(action, subActions.toList())

    init {
        templatePresentation.isPerformGroup = true
        templatePresentation.isPopupGroup = true
    }

    override fun actionPerformed(e: AnActionEvent) = action.actionPerformed(e)
}