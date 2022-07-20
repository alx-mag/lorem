package com.github.alxmag.loremipsumgenerator.action.base

import com.github.alxmag.loremipsumgenerator.MyBundle
import com.github.alxmag.loremipsumgenerator.action.recent.LoremMemorizableAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Separator

/**
 * Base class for lorem action groups.
 * This group is executable by delegating performance to provided [action].
 */
abstract class LoremPerformableActionGroupBase : DefaultActionGroup(), LoremMemorizableAction.BySelfId {

    abstract val action: AnAction

    init {
        templatePresentation.isPerformGroup = true
        templatePresentation.isPopupGroup = true
    }

    abstract override fun getChildren(e: AnActionEvent?): Array<AnAction>

    override fun actionPerformed(e: AnActionEvent) = action.actionPerformed(e)

    /**
     * Contains sub-actions with 'Recent' separator
     */
    abstract class WithRecentActions : LoremPerformableActionGroupBase() {
        final override fun getChildren(e: AnActionEvent?): Array<AnAction> {
            val historyActions = getHistoryActions()
            return buildList {
                if (historyActions.isNotEmpty()) {
                    add(Separator(MyBundle.message("separator.recent")))
                }
                addAll(historyActions)
            }.toTypedArray()
        }

        protected abstract fun getHistoryActions(): List<AnAction>
    }
}