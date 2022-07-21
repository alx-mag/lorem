package com.github.alxmag.loremipsumgenerator.service

import com.github.alxmag.loremipsumgenerator.LoremNotifications
import com.github.alxmag.loremipsumgenerator.action.LoremGeneratePopUpAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.keymap.KeymapUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.xml.util.XmlStringUtil.wrapInHtmlTag

// TODO the plugin will be installed dynamically so should be implemented a way to show Welcome message without IDE restart.
//  I think this can be implemented using com.intellij.ide.plugins.DynamicPluginListener
class LoremWelcomeMessageActivity : StartupActivity.Background {

    override fun runActivity(project: Project) {
        val pluginSettingsManager = LoremPluginSettingsManager.getInstance()
        if (pluginSettingsManager.state.showWelcomeMessage) {
            showWelcomeNotification(project)
            pluginSettingsManager.state.showWelcomeMessage = false
        }
    }

    companion object {
        fun showWelcomeNotification(project: Project) {
            val generateGroupId = "Generate"
            val actionManager = ActionManager.getInstance()

            val loremPopUpActionName = actionManager.getAction(LoremGeneratePopUpAction.ID).templateText!!

            val generateGroupName = sequenceOf(
                actionManager.getAction("CodeMenu").templateText!!,
                actionManager.getAction(generateGroupId).templateText!!,
                loremPopUpActionName
            ).joinToString(" | ")

            val generateGroupShortcut = KeymapUtil.getShortcutText(generateGroupId)
                .takeIf { it.isNotEmpty() }
                ?.takeIf { it != "<no shortcut>" }

            val loremPopupShortcut = KeymapUtil.getShortcutText(LoremGeneratePopUpAction.ID)
                .takeIf { it.isNotEmpty() }
                ?.takeIf { it != "<no shortcut>" }

            val message = buildString {
                append("Here is how to find the plugin actions:")
                append("<ul>")
                append("<li>")
                append("By calling ")
                append(generateGroupName.wrapActionName())
                if (generateGroupShortcut != null) {
                    append(" ")
                    append(generateGroupShortcut.wrapShortcutText())
                }
                append(" action;")
                append("</li>")

                append("<li>")
                append("By calling ")
                append(loremPopUpActionName.wrapActionName())
                if (loremPopupShortcut != null) {
                    append(" ")
                    append(loremPopupShortcut.wrapShortcutText())
                }
                append(" action in text editor;")

                append("</li>")

                append("<li>")
                append("By searching actions using <i>Lorem:</i> keyword.")
                append("</li>")
                append("</ul>")
            }

            LoremNotifications.getGroup()
                .createNotification("Thank you for Lorem installation", message, NotificationType.INFORMATION)
                .also {
                    it.dropDownText = "Drop down"
                }
                .notify(project)
        }

        private fun String.wrapActionName() = wrapInHtmlTag(this, "b")

        private fun String.wrapShortcutText() = "($this)"
    }
}

/**
 * Action for testing
 */
class ShowLoremWelcomeMessage : AnAction("Show Lorem Welcome Message") {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let(LoremWelcomeMessageActivity::showWelcomeNotification)
    }
}