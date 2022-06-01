package com.github.alxmag.loremipsumgenerator.generate

import com.github.alxmag.loremipsumgenerator.action.SentenceActionGroup
import com.github.alxmag.loremipsumgenerator.services.LoremIpsumService
import com.github.alxmag.loremipsumgenerator.util.LoremGenerateActionBuilder.Companion.loremAction
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

/**
 * Provides an action group that contains all available generate actions
 */
// TODO remove
@Service
class LoremGenerateActionsManager(private val project: Project) {

    private val loremService = LoremIpsumService.getInstance(project)
    private val actionManager = ActionManager.getInstance()

    fun getActionGroup() = DefaultActionGroup(

        actionManager.getAction(SentenceActionGroup.ID),
        loremAction {
            templatePresentation {
                text = "City"
                description = "Generate random city name"
            }
            generateText { loremService.city }
        },

        loremAction {
            templatePresentation {
                text = "Name"
                description = "Generate random name"
            }
            generateText {
                loremService.name
            }

            subAction {
                templatePresentation {
                    text = "Female Name"
                    description = "Generate random female name"
                }
                generateText {
                    loremService.nameFemale
                }
            }

            subAction {
                templatePresentation {
                    text = "Male Name"
                    description = "Generate random male name"
                }
                generateText {
                    loremService.nameMale
                }
            }
        }
    )

    companion object {
        fun getInstance(project: Project) = project.service<LoremGenerateActionsManager>()
    }
}